package com.petproject.musicguessr.model.outrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hit {
    private String index;
    private String type;
    private Result result;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        private String id;
        private String url;
        @JsonProperty("full_title")
        private String fullTitle;
    }

    public static final class Builder {
        private String index;
        private String type;
        private String id;
        private String url;
        private String fullTitle;

        public Builder setIndex(String index){
            this.index = index;
            return this;
        }

        public Builder setType(String type){
            this.type = type;
            return this;
        }

        public Builder setResult(String id){
            this.id = id;
            return this;
        }

        public Builder setUrl(String url){
            this.url = url;
            return this;
        }

        public Builder setFullTitle(String fullTitle){
            this.fullTitle = fullTitle;
            return this;
        }

        public Hit build() {
            Hit obj = new Hit();
            obj.index = this.index;
            obj.type = this.type;
            obj.result = new Result(this.id, this.url, this.fullTitle);
            return obj;
        }
    }
}
