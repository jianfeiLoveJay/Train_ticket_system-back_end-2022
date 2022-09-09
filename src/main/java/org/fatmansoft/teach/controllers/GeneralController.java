package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.models.User;
import org.fatmansoft.teach.models.admin;
import org.fatmansoft.teach.models.customer;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.UserRepository;
import org.fatmansoft.teach.repository.adminRepository;
import org.fatmansoft.teach.repository.customerRepository;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/general")
public class GeneralController {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    adminRepository adminrepository;
    @Autowired
    customerRepository customerrepository;


    //注册用户
    @PostMapping("/createuser")
    public DataResponse createuser(@Valid @RequestBody DataRequest dataRequest) {
        Map form = dataRequest.getMap("form");
        String cusNum = (String) form.get("cusNum");
        String cusName = (String) form.get("cusName");
        String cussex = (String) form.get("cussex");
        String cusemail = (String) form.get("cusemail");
        String cusphone = (String) form.get("cusphone");
        String cusaddress = (String) form.get("cusaddress");

        customer sdu=new customer() ;
        sdu.setCusNum(cusNum);
        sdu.setCusName(cusName);
        sdu.setCussex(cussex);
        sdu.setCusemail(cusemail);
        sdu.setCusphone(cusphone);
        sdu.setCusaddress(cusaddress);
        customerrepository.save(sdu);
        return CommonMethod.getReturnMessageOK();
    }

    //注册管理员
    @PostMapping("/createadmin")
    public DataResponse createadmin(@Valid @RequestBody DataRequest dataRequest) {
        Map form = dataRequest.getMap("form");
        String adminNum = (String) form.get("adminNum");
        String adminName = (String) form.get("adminName");

        admin sdu=new admin() ;
        sdu.setAdminNum(adminNum);
        sdu.setAdminName(adminName);
        adminrepository.save(sdu);
        return CommonMethod.getReturnMessageOK();
    }

    //修改密码
    @PostMapping("/changePassword")
    public DataResponse changePassword(@Valid @RequestBody DataRequest dataRequest) {
        Integer userId= CommonMethod.getUserId();
        if(userId == null)
            return CommonMethod.getReturnMessageError("不存在该用户");
        String oldPassword = dataRequest.getString("oldPassword");
        String newPassword = dataRequest.getString("newPassword");
        User u = userRepository.findById(userId).get();
        if(!encoder.matches(oldPassword, u.getPassword())) {
            return CommonMethod.getReturnMessageError("原密码错误");
        }
        u.setPassword(encoder.encode(newPassword));
        userRepository.save(u);
        return CommonMethod.getReturnMessage("0","修改成功");
    }

    //测试例子
//    @PostMapping("/admin")
//    public ResponseEntity<?> adminAccess(HttpSession session) {
//        LocalDateTime ts = (LocalDateTime)session.getAttribute("ts");
//        Map<String, String> res = new HashMap<String, String>();
//        if (ts == null) {
//            res.put("ts", "");
//        } else {
//            res.put("ts", ts.toString());
//        }
//        session.setAttribute("ts",  LocalDateTime.now());
//        return ResponseEntity.ok(new DataResponse(
//                "ok",
//                res
//        ));
//    }



}