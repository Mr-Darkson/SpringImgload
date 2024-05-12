package com.example.imgdownload.controller;

import com.example.imgdownload.model.Person;
import com.example.imgdownload.service.PersonService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.gson.GsonProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/profile")
@Data
public class ProfileController {

    @Autowired
    private Environment environment;

    @Value("${upload.path}")
    private String uploadDirectoryPath;

    private final PersonService personService;

    @GetMapping("/{id}")
    public String profile(Model model, @PathVariable int id) {
        Person person = personService.findOne(id);
        model.addAttribute("person", person);
        return "profile/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        Person person = personService.findOne(id);
        model.addAttribute("person", person);
        return "profile/edit";
    }

    @PostMapping("/{id}")
    public String changePerson(@ModelAttribute("person") Person person,
                               @PathVariable("id") int id,
                               @RequestParam("file")MultipartFile file) throws IOException {

        if(!file.isEmpty()) {
            String dirPath = System.getProperty("user.dir") + "/src/main/upload/";

            //dirPath = dirPath.replaceAll("\\\\", "/");
            File uploadDir = new File(dirPath);

            if(!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(dirPath + "/" + resultFileName));

            person.setAvatar_name(resultFileName);
            personService.update(id, person);

        }
        else personService.updateLogin(id, person.getLogin());

        return "redirect:/profile/" + id;
    }
}
