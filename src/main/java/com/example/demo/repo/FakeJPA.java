package com.example.demo.repo;

import reactor.core.publisher.Flux;

public class FakeJPA<T extends FakeJPA.JPAElement> {
    protected Flux<T> data = Flux.empty();

    public Flux<T> all() {
        return data;
    }

    public void remove(long id) {
        data = data.filter(e -> e.getId() != id);
    }

    public T findById(long id) {
        return data.filter(e -> e.getId() == id).singleOrEmpty().block();
    }

    public T save(T t) {
        if(findById(t.getId()) != null) {
            data = data.map(e -> e.getId() == t.getId() ? t : e);
        } else {
            data.count().blockOptional().ifPresent(t::setId);
            data = data.concatWithValues(t);
        }

        return t;
    }

    public interface JPAElement {
        long getId();
        void setId(long id);
    }
}