// src/main/java/io/github/yo1000/sss/controller/page/MemoController.java
package io.github.yo1000.sss.controller.page;

import io.github.yo1000.sss.aspect.ExceptionControllerAdvice;
import io.github.yo1000.sss.model.Memo;
import io.github.yo1000.sss.service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("memo")
public class MemoController {
    private MemoService memoService;

    @Autowired
    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @RequestMapping("")
    public String get(Model model) {
        model.addAttribute("items", getMemoService().readMemos());
        return "memo";
    }

    @RequestMapping("{author}")
    public String get(@PathVariable String author,
                      Model model) {
        model.addAttribute("items", getMemoService().readMemosFilteredAuthor(author));
        return "memo";
    }

    @RequestMapping("param/{memo:[a-zA-Z0-9]+}")
    public String getParams(@PathVariable String memo,
                            @RequestParam(required = false, defaultValue = "C6DefaultAuthor") String author,
                            Model model) {
        List<Memo> items = new ArrayList<>();
        items.add(getMemoService().takeMemo(memo, author));

        model.addAttribute("items", items);
        return "memo";
    }

    @RequestMapping("error")
    public String getError(Model model) {
        throw new ExceptionControllerAdvice.MemoException();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String post(@ModelAttribute Memo item,
                       Model model) {
        getMemoService().writeMemo(item.getMemo(), item.getAuthor());
        return "redirect:/memo";
    }

    public MemoService getMemoService() {
        return memoService;
    }
}
