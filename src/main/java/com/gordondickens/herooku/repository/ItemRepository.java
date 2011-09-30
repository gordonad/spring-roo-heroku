package com.gordondickens.herooku.repository;

import com.gordondickens.herooku.domain.Item;
import org.springframework.roo.addon.layers.repository.jpa.RooRepositoryJpa;

@RooRepositoryJpa(domainType = Item.class)
public interface ItemRepository {
}
