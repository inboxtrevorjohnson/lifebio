package au.com.lifebio.lifebiocommon.common.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Trevor on 2018/06/18.
 */
@RestController
public class CommonControllerImpl implements CommonController {

    @RequestMapping(value="/error")
    public String error() {
        return "error";
    }
}
