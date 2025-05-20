package com.petproject.songguessr.core.processor;

import com.petproject.songguessr.core.handler.BroadcastEventHandler;
import com.petproject.songguessr.core.handler.TargetEventHandler;
import com.petproject.songguessr.core.room.model.Player;
import com.petproject.songguessr.model.BaseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * Реализация процессора событий для обработки {@link BaseEvent} с использованием зарегистрированных обработчиков.
 * <p>
 * Обеспечивает двунаправленную обработку событий:
 * <ul>
 *   <li><b>Целевая обработка</b> - через {@link TargetEventHandler}, работающие с конкретным игроком ({@link Player})</li>
 *   <li><b>Широковещательная обработка</b> - через {@link BroadcastEventHandler}, работающие с набором игроков</li>
 * </ul>
 * </p>
 *
 * <p>Порядок обработки:
 * <ol>
 *   <li>Последовательно проверяет все целевые обработчики через {@link TargetEventHandler#canHandle(BaseEvent)}</li>
 *   <li>Для подходящих обработчиков выполняет {@link TargetEventHandler#handle(BaseEvent, Player)}</li>
 *   <li>Аналогично обрабатывает широковещательные обработчики для набора игроков</li>
 * </ol>
 * </p>
 *
 * @implSpec
 * Не гарантирует порядок обработки внутри групп (target/broadcast). Для контроля порядка использовать сортировку обработчиков.
 *
 * @see EventProcessor
 * @see TargetEventHandler
 * @see BroadcastEventHandler
 */
public class EventProcessorImpl implements EventProcessor<BaseEvent<?>> {
    private static final Logger log = LoggerFactory.getLogger(EventProcessorImpl.class);
    private final List<TargetEventHandler<?>> targetEventHandlers;
    private final List<BroadcastEventHandler<?>> broadcastEventHandlers;

    public EventProcessorImpl(
            List<TargetEventHandler<?>> targetEventHandlers,
            List<BroadcastEventHandler<?>> broadcastEventHandlers
    ) {
        this.targetEventHandlers = targetEventHandlers;
        this.broadcastEventHandlers = broadcastEventHandlers;
    }

    @Override
    public void process(BaseEvent<?> event, Player player, Set<Player> players) {
        targetEventHandlers.forEach(handler -> handleTarget(event, player, handler));
        broadcastEventHandlers.forEach(handler -> handleBroadcast(event, players, handler));
    }

    private <T extends BaseEvent<?>> void handleTarget(
            BaseEvent<?> event,
            Player player,
            TargetEventHandler<T> handler
    ) {
        if (handler.canHandle(event)) {
            handler.handle(handler.getType().cast(event), player);
        }
    }

    private <T extends BaseEvent<?>> void handleBroadcast(
            BaseEvent<?> event,
            Set<Player> players,
            BroadcastEventHandler<T> handler
    ) {
        if (handler.canHandle(event)) {
            handler.handle(handler.getType().cast(event), players);
        }
    }
}
