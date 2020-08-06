package com.kangjun.threadpool.future;

import lombok.Data;

@Data
public class Product {

    private Long id;

    private String name;

    public static Product of(Long id, String name) {
        return new Builder()
                .id(id)
                .name(name)
                .build();
    }

    private static class Builder {
        private Product result;

        public Builder() {
            this.result = new Product();
        }

        public Builder id(Long id) {
            this.result.setId(id);
            return this;
        }

        public Builder name(String name) {
            this.result.setName(name);
            return this;
        }

        public Product build() {
            return this.result;
        }
    }
}
