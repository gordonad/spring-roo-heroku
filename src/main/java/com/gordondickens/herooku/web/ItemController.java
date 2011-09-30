package com.gordondickens.herooku.web;

import com.gordondickens.herooku.domain.Item;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/items")
@Controller
@RooWebScaffold(path = "items", formBackingObject = Item.class)
public class ItemController {
}
