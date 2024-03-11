package com.example.comp1640.Controller;

import com.example.comp1640.model.Feedback;
import com.example.comp1640.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/FeedBack")
public class FeedBackController {
    @Autowired
    FeedbackRepository re;

    @PostMapping("/Create")
    public String CreateFeedback(@RequestParam("id") String id, @RequestParam("content") String content
            , @RequestParam("userid") String userid, Model model){
        re.CreateFeedBack(id,content,userid);
        return "redirect:/FeedBack/View";
    }
    @GetMapping("/CreateFeedback")
    public String CreatFul(){
        return "Feedback/CreateFeedback";
    }

    @GetMapping("/Update/{id}") // Corrected mapping without the trailing slash
    public String update(@PathVariable String id, Model model) {
//        System.out.println(id);
        Feedback fe = re.ReturnFeedback(id);
        model.addAttribute("Feedback", fe);
        return "Feedback/UpdateFeedback";
    }
    @PostMapping("/Updating")
    public String UpdatePostFeedback(@RequestParam("id") String id,@RequestParam("content") String content
            ,@RequestParam("userid") String  userid, Model model){
        System.out.println(id);
        System.out.println(content);
        System.out.println(userid);
        re.UpdateFeedBack(id,content,userid);
        return "redirect:/FeedBack/View";
    }

    @GetMapping("/View")
    public String View(Model model){
        List<Feedback> Faculties = re.ReturnFeedBacks();
        model.addAttribute("Fals",Faculties);
        return "Feedback/ViewFeedback";
    }

    @PostMapping("/Delete")
    public String Delete(@RequestParam("id") String id) {
        re.DeleteFal(id);
        return "redirect:/FeedBack/View";
    }
}
