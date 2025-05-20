package com.petproject.songguessr.core.processor;

import com.petproject.songguessr.core.room.model.Player;
import com.petproject.songguessr.model.BaseEvent;

import java.util.Set;

/**
 * Интерфейс для обработки событий с возможностью адресной и массовой рассылки.
 * <p>
 * Определяет контракт обработки событий типа {@code T} в контексте:
 * <ul>
 *   <li>Отдельного игрока ({@link Player}) - для персонализированной обработки</li>
 *   <li>Набора игроков - для широковещательной рассылки</li>
 * </ul>
 * </p>
 *
 * <p>Типовая реализация: {@link EventProcessorImpl}
 *
 * @param <T> Тип обрабатываемого события, обычно наследник {@link BaseEvent}
 */
public interface EventProcessor<T> {
    /**
     * Основной метод обработки события.
     * <p>
     * Реализации должны обеспечивать:
     * <ol>
     *   <li>Распределение обработки между зарегистрированными обработчиками</li>
     *   <li>Проверку возможности обработки через {@code canHandle()} методов обработчиков</li>
     *   <li>Безопасное приведение типа события перед обработкой</li>
     * </ol>
     * </p>
     *
     * @param event    Событие для обработки (не null)
     * @param player   Целевой игрок для персональных действий (не null)
     * @param players  Набор игроков для массовых операций (не null)
     */
    void process(T event, Player player, Set<Player> players);
}
