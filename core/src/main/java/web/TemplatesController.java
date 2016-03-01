package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import services.TemplateService;

import java.util.Map;

@Controller
@RequestMapping("/template")
public class TemplatesController {

    @Autowired
    TemplateService templateService;

    private static final String defaultTemplateName = "firstTemplate";

    @RequestMapping(method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map getDefaultTemplate() {

    return templateService.getTemplateByName(defaultTemplateName);

    }
}
