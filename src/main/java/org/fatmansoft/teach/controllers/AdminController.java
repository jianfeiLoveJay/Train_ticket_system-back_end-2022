package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.models.*;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.*;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.fatmansoft.teach.models.EUserType;
import org.fatmansoft.teach.models.User;
import org.fatmansoft.teach.models.UserType;
import org.fatmansoft.teach.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.fatmansoft.teach.payload.request.LoginRequest;
import org.fatmansoft.teach.payload.request.SignupRequest;
import org.fatmansoft.teach.payload.response.JwtResponse;
import org.fatmansoft.teach.payload.response.MessageResponse;
import org.fatmansoft.teach.repository.UserRepository;
import org.fatmansoft.teach.security.jwt.JwtUtils;
import org.fatmansoft.teach.security.services.UserDetailsImpl;

// origins： 允许可访问的域列表
// maxAge:准备响应前的缓存持续的最大时间（以秒为单位）。
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")

public class AdminController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserRepository  userRepository;
    @Autowired
    customerRepository customerrepository;
    @Autowired
    trainRepository  trainrepository;
    @Autowired
    seatRepository seatrepository;
    @Autowired
    orderRepository orderrepository;
    @Autowired
    passengerRepository passengerrepository;
    @Autowired
    UserTypeRepository userTypeRepository;

    //获取所有列车列表
    @PostMapping("/gettrainList")
    public DataResponse gettrainList(@Valid @RequestBody DataRequest dataRequest) {
        String begin = dataRequest.getString("begin");
        String end = dataRequest.getString("end");
        String time = dataRequest.getString("time");
        List list=null;
        if(begin.equals("") && end.equals("") && time.equals(""))
            list = trainrepository.findAll();
        else if(!begin.equals("") && end.equals("") && time.equals(""))
            list = trainrepository.findtrainListBybegin(begin);
        else if(begin.equals("") && !end.equals("") && time.equals(""))
            list = trainrepository.findtrainListByend(end);
        else if(begin.equals("") && end.equals("") && !time.equals(""))
            list = trainrepository.findtrainListBytime(time);
        else if(!begin.equals("") && !end.equals("") && time.equals(""))
            list = trainrepository.findtrainListBybeginend(begin,end);
        else if(!begin.equals("") && end.equals("") && !time.equals(""))
            list = trainrepository.findtrainListBybegintime(begin,time);
        else if(begin.equals("") && !end.equals("") && !time.equals(""))
            list = trainrepository.findtrainListByendtime(end,time);
        else
            list = trainrepository.findtrainListBybeginendtime(begin,end,time);
        List dataList = new ArrayList();
        if(list == null || list.size()== 0) {
            CommonMethod.getReturnData(dataList);
        }
        Map m;
        train c;
        for(int i = 0; i < list.size();i++) {
            c = (train) list.get(i);
            m = new HashMap();
            m.put("trainId",c.getTrain_id());
            m.put("trainNum",c.getTrain_num());
            m.put("trainName",c.getTrain_name());
            m.put("traintype",c.getTrain_type());
            m.put("trainroom",c.getTrain_room());
            m.put("trainroomsize",c.getTrain_roomSize());
            m.put("trainbeginplace",c.getTrain_beginPlace());
            m.put("trainendplace",c.getTrain_endPlace());
            m.put("trainbeginday",c.getTrain_beginDay());
            m.put("trainendday",c.getTrain_endDay());
            m.put("trainbegintime",c.getTrain_beginTime());
            m.put("trainendtime",c.getTrain_endTime());
            dataList.add(m);
        }
        return CommonMethod.getReturnData(dataList);
    }

    //获取改签列车列表
    @PostMapping("/getchangetrainList")
    public DataResponse getchangetrainList(@Valid @RequestBody DataRequest dataRequest) {
        String begin = dataRequest.getString("begin");
        String end = dataRequest.getString("end");
        String trainNum = dataRequest.getString("trainNum");
        List list=null;
        list = trainrepository.findtrainListBybeginend_trainNum(begin,end,trainNum);

        List dataList = new ArrayList();
        if(list == null || list.size()== 0) {
            CommonMethod.getReturnData(dataList);
        }
        Map m;
        train c;
        for(int i = 0; i < list.size();i++) {
            c = (train) list.get(i);
            m = new HashMap();
            m.put("trainId",c.getTrain_id());
            m.put("trainNum",c.getTrain_num());
            m.put("trainName",c.getTrain_name());
            m.put("traintype",c.getTrain_type());
            m.put("trainroom",c.getTrain_room());
            m.put("trainroomsize",c.getTrain_roomSize());
            m.put("trainbeginplace",c.getTrain_beginPlace());
            m.put("trainendplace",c.getTrain_endPlace());
            m.put("trainbeginday",c.getTrain_beginDay());
            m.put("trainendday",c.getTrain_endDay());
            m.put("trainbegintime",c.getTrain_beginTime());
            m.put("trainendtime",c.getTrain_endTime());
            dataList.add(m);
        }
        return CommonMethod.getReturnData(dataList);
    }


    //获取当前列车信息
    @PostMapping("/gettrainInfo")
    public DataResponse gettrainInfo(@Valid @RequestBody DataRequest dataRequest) {
        Integer trainId = dataRequest.getInteger("trainId");
        train c= null;
        Optional<train> op;
        if(trainId != null) {
            op= trainrepository.findById(trainId);
            if(op.isPresent()) {
                c = op.get();
            }
        }
        Map form = new HashMap();
        if(c != null) {
            form.put("trainId",c.getTrain_id());
            form.put("trainNum",c.getTrain_num());
            form.put("trainName",c.getTrain_name());
            form.put("traintype",c.getTrain_type());
            form.put("trainroom",c.getTrain_room());
            form.put("trainroomsize",c.getTrain_roomSize());
            form.put("trainbeginplace",c.getTrain_beginPlace());
            form.put("trainendplace",c.getTrain_endPlace());
            form.put("trainbeginday",c.getTrain_beginDay());
            form.put("trainendday",c.getTrain_endDay());
            form.put("trainbegintime",c.getTrain_beginTime());
            form.put("trainendtime",c.getTrain_endTime());
        }
        return CommonMethod.getReturnData(form);
    }

    // 列车添加,车厢座位初始化
    @PostMapping("/trainadd")
    public DataResponse trainadd(@Valid @RequestBody DataRequest dataRequest) {
        Map form = dataRequest.getMap("form");
        Integer trainId = (Integer)form.get("trainId");
        String trainNum = (String)form.get("trainNum");
        String trainName = (String)form.get("trainName");
        String traintype = (String )form.get("traintype");
        String trainroom = (String)form.get("trainroom");
        String trainroomsize = (String )form.get("trainroomsize");
        String trainbeginplace= (String )form.get("trainbeginplace");
        String trainendplace = (String)form.get("trainendplace");
        String trainbeginday = (String )form.get("trainbeginday");
        String trainendday = (String)form.get("trainendday");
        String trainbegintime = (String )form.get("trainbegintime");
        String trainendtime = (String)form.get("trainendtime");

        train c= null;
        Optional<train> op;
        if(trainId != null) {
            op= trainrepository.findById(trainId);
            if(op.isPresent()) {
                c = op.get();
            }
        }
        if(c == null) {
            c = new train();
        }
        c.setTrain_num(trainNum);
        c.setTrain_name(trainName);
        c.setTrain_type(traintype);
        c.setTrain_room(trainroom);
        c.setTrain_roomSize(trainroomsize);
        c.setTrain_beginPlace(trainbeginplace);
        c.setTrain_endPlace(trainendplace);
        c.setTrain_beginDay(trainbeginday);
        c.setTrain_endDay(trainendday);
        c.setTrain_beginTime(trainbegintime);
        c.setTrain_endTime(trainendtime);
        trainrepository.save(c);

        Integer room=Integer.parseInt(trainroom);
        Integer roomsize=Integer.parseInt(trainroomsize);
        for(int i=1;i<room+1;i++) {
            for(int j=1;j<roomsize+1;j++) {
                seat s = new seat();
                s.setTrain_Num(trainNum);
                s.setTrain_roomNum(i+"");
                s.setSeat_num("A"+j);
                s.setSeat_price("100");
                s.setSeat_type("三等座");
                s.setSeat_state("未预定");
                seatrepository.save(s);
            }
        }
        return CommonMethod.getReturnData(c.getTrain_id());
    }

    // 列车信息修改
    @PostMapping("/trainedit")
    public DataResponse trainedit(@Valid @RequestBody DataRequest dataRequest) {
        Map form = dataRequest.getMap("form");
        Integer trainId = (Integer)form.get("trainId");
        String trainNum = (String)form.get("trainNum");
        String trainName = (String)form.get("trainName");
        String traintype = (String )form.get("traintype");
        String trainroom = (String)form.get("trainroom");
        String trainbeginplace= (String )form.get("trainbeginplace");
        String trainendplace = (String)form.get("trainendplace");
        String trainbeginday = (String )form.get("trainbeginday");
        String trainendday = (String)form.get("trainendday");
        String trainbegintime = (String )form.get("trainbegintime");
        String trainendtime = (String)form.get("trainendtime");

        train c= null;
        Optional<train> op;
        if(trainId != null) {
            op= trainrepository.findById(trainId);
            if(op.isPresent()) {
                c = op.get();
            }
        }
        if(c == null) {
            c = new train();
        }
        c.setTrain_num(trainNum);
        c.setTrain_name(trainName);
        c.setTrain_type(traintype);
        c.setTrain_room(trainroom);
        c.setTrain_beginPlace(trainbeginplace);
        c.setTrain_endPlace(trainendplace);
        c.setTrain_beginDay(trainbeginday);
        c.setTrain_endDay(trainendday);
        c.setTrain_beginTime(trainbegintime);
        c.setTrain_endTime(trainendtime);
        trainrepository.save(c);
        return CommonMethod.getReturnData(c.getTrain_id());
    }

    //  列车信息删除
    @PostMapping("/trainDelete")
    public DataResponse trainDelete(@Valid @RequestBody DataRequest dataRequest) {
        Integer trainId = dataRequest.getInteger("trainId");
        train c= null;
        Optional<train> op;
        if(trainId != null) {
            op= trainrepository.findById(trainId);
            if(op.isPresent()) {
                c = op.get();
            }
        }
        List list;
        list = orderrepository.findorderListBytrainNum(c.getTrain_num());
        if( list!=null&&list.size()!= 0){
            return CommonMethod.getReturnMessageError("列车已有订单，无法删除!");
        }
        if(c != null && (list == null || list.size()== 0)) {
            trainrepository.delete(c);
        }
        return CommonMethod.getReturnMessageOK();
    }

    //获取列车当前车厢座次列表
    @PostMapping("/getallseatList")
    public DataResponse getallseatList(@Valid @RequestBody DataRequest dataRequest) {
        String trainNum = dataRequest.getString("trainNum");
        String trainroomnum = dataRequest.getString("trainroomnum");
        List list=null;
        list = seatrepository.findseatListBytrainNumtrainroomnum(trainNum,trainroomnum);
        List dataList = new ArrayList();
        if(list == null || list.size()== 0) {
            CommonMethod.getReturnData(dataList);
        }
        Map m;
        seat c;
        for(int i = 0; i < list.size();i++) {
            c = (seat) list.get(i);
            m = new HashMap();
            m.put("trainNum",c.getTrain_Num());
            m.put("trainroomnum",c.getTrain_roomNum());
            m.put("seatId",c.getSeat_id());
            m.put("seatnum",c.getSeat_num());
            m.put("seattype",c.getSeat_type());
            m.put("seatprice",c.getSeat_price());
            m.put("seatstate",c.getSeat_state());
            dataList.add(m);
        }
        return CommonMethod.getReturnData(dataList);
    }

    //获取列车当前车厢未预定座次列表
    @PostMapping("/getseatList")
    public DataResponse getseatList(@Valid @RequestBody DataRequest dataRequest) {
        String trainNum = dataRequest.getString("trainNum");
        String trainroomnum = dataRequest.getString("trainroomnum");
        List list=null;
        list = seatrepository.findnoreserveseatListBytrainNumtrainroomnum(trainNum,trainroomnum);
        List dataList = new ArrayList();
        if(list == null || list.size()== 0) {
            CommonMethod.getReturnData(dataList);
        }
        Map m;
        seat c;
        for(int i = 0; i < list.size();i++) {
            c = (seat) list.get(i);
            m = new HashMap();
            m.put("trainNum",c.getTrain_Num());
            m.put("trainroomnum",c.getTrain_roomNum());
            m.put("seatId",c.getSeat_id());
            m.put("seatnum",c.getSeat_num());
            m.put("seattype",c.getSeat_type());
            m.put("seatprice",c.getSeat_price());
            m.put("seatstate",c.getSeat_state());
            dataList.add(m);
        }
        return CommonMethod.getReturnData(dataList);
    }

    //当前车厢座位查询
    @PostMapping("/getroomseatleft")
    public DataResponse getroomseatleft(@Valid @RequestBody DataRequest dataRequest) {
        String trainNum = dataRequest.getString("trainNum");
        String trainroomnum = dataRequest.getString("trainroomnum");
        List list=null;
        list = seatrepository.findseatListBytrainNumtrainroomnum(trainNum,trainroomnum);
        List dataList = new ArrayList();
        if(list == null || list.size()== 0) {
            CommonMethod.getReturnData(dataList);
        }
        Map form = new HashMap();
        if(list != null) {
            form.put("trainroomseatleft",list.size());
        }
        return CommonMethod.getReturnData(form);
    }


    //获取当前座次信息
    @PostMapping("/getseatInfo")
    public DataResponse getseatInfo(@Valid @RequestBody DataRequest dataRequest) {
        Integer seatId = dataRequest.getInteger("seatId");
        seat c= null;
        Optional<seat> op;
        if(seatId != null) {
            op= seatrepository.findById(seatId);
            if(op.isPresent()) {
                c = op.get();
            }
        }
        Map form = new HashMap();
        if(c != null) {
            form.put("trainNum",c.getTrain_Num());
            form.put("trainroomnum",c.getTrain_roomNum());
            form.put("seatId",c.getSeat_id());
            form.put("seatnum",c.getSeat_num());
            form.put("seattype",c.getSeat_type());
            form.put("seatprice",c.getSeat_price());
            form.put("seatstate",c.getSeat_state());
        }
        return CommonMethod.getReturnData(form);
    }

    // 座次添加
    @PostMapping("/seatadd")
    public DataResponse seatadd(@Valid @RequestBody DataRequest dataRequest) {
        Map form = dataRequest.getMap("form");
        String trainNum = (String)form.get("trainNum");
        String trainroomnum = (String)form.get("trainroomnum");
        String seatposition = (String)form.get("seatposition");
        String seattype = (String)form.get("seattype");
        String  seatprice = (String )form.get("seatprice");
        String  seatstate = (String )form.get("seatstate");
        String seatquantity = (String)form.get("seatquantity");

        String max = seatrepository.findmaxseatnumBytrainNumtrainroomnum(trainNum,trainroomnum);
        String maxsub=max.substring(1);
        Integer maxint=Integer.parseInt(maxsub);
        Integer seatquantityint=Integer.parseInt(seatquantity);
        for(int j=1;j<seatquantityint+1;j++) {
            seat c = new seat();
            c.setTrain_Num(trainNum);
            c.setTrain_roomNum(trainroomnum);
            c.setSeat_num(seatposition+(j+maxint));
            c.setSeat_type(seattype);
            c.setSeat_price(seatprice);
            c.setSeat_state(seatstate);
            seatrepository.save(c);
        }
        return CommonMethod.getReturnMessageOK();
    }

    // 座次信息修改
    @PostMapping("/seatedit")
    public DataResponse seatedit(@Valid @RequestBody DataRequest dataRequest) {
        Map form = dataRequest.getMap("form");
        Integer seatId = (Integer)form.get("seatId");
        String trainNum = (String)form.get("trainNum");
        String trainroomnum = (String)form.get("trainroomnum");
        String seatnum = (String)form.get("seatnum");
        String seattype = (String)form.get("seattype");
        String  seatprice = (String )form.get("seatprice");
        String  seatstate = (String )form.get("seatstate");

        seat c= null;
        Optional<seat> op;
        if(seatId != null) {
            op= seatrepository.findById(seatId);
            if(op.isPresent()) {
                c = op.get();
            }
        }
        if(c == null) {
            c = new seat();
        }
        c.setTrain_Num(trainNum);
        c.setTrain_roomNum(trainroomnum);
        c.setSeat_num(seatnum);
        c.setSeat_type(seattype);
        c.setSeat_price(seatprice);
        c.setSeat_state(seatstate);
        seatrepository.save(c);
        return CommonMethod.getReturnData(c.getSeat_id());
    }

    //  座次信息删除
    @PostMapping("/seatDelete")
    public DataResponse seatDelete(@Valid @RequestBody DataRequest dataRequest) {
        Integer seatId = dataRequest.getInteger("seatId");
        seat c= null;
        Optional<seat> op;
        if(seatId != null) {
            op= seatrepository.findById(seatId);
            if(op.isPresent()) {
                c = op.get();
            }
        }
        if(c != null) {
            seatrepository.delete(c);
        }
        return CommonMethod.getReturnMessageOK();

    }

    //获取当前订单信息
    @PostMapping("/getorderInfo")
    public DataResponse getorderInfo(@Valid @RequestBody DataRequest dataRequest) {
        Integer orderId = dataRequest.getInteger("orderId");
        trainorder c= null;
        Optional<trainorder> op;
        if(orderId != null) {
            op= orderrepository.findById(orderId);
            if(op.isPresent()) {
                c = op.get();
            }
        }
        Map form = new HashMap();
        if(c != null) {
            form.put("orderId",c.getOrder_id());
            form.put("ordernum",c.getOrder_num());
            form.put("orderstate",c.getOrder_state());
            form.put("ordertype",c.getOrder_type());
            form.put("ordertime",c.getOrder_time());
            form.put("usernum",c.getUser_num());
            form.put("passengername",c.getPassenger_name());
            form.put("passengernum",c.getPassenger_num());
            form.put("passengerphonenum",c.getPassenger_phoneNum());
            form.put("trainNum",c.getTrain_num());
            form.put("trainName",c.getTrain_name());
            form.put("traintype",c.getTrain_type());
            form.put("trainroomnum",c.getTrain_roomNum());
            form.put("seatId",c.getSeat_id());
            form.put("seatnum",c.getSeat_num());
            form.put("seattype",c.getSeat_type());
            form.put("seatprice",c.getSeat_price());
            form.put("seatstate",c.getSeat_state());
            form.put("trainbeginplace",c.getTrain_beginPlace());
            form.put("trainendplace",c.getTrain_endPlace());
            form.put("trainbeginday",c.getTrain_beginDay());
            form.put("trainendday",c.getTrain_endDay());
            form.put("trainbegintime",c.getTrain_beginTime());
            form.put("trainendtime",c.getTrain_endTime());
        }
        return CommonMethod.getReturnData(form);
    }

    // 订单信息提交
    @PostMapping("/ordermanageSubmit")
    public DataResponse ordermanageSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Map form = dataRequest.getMap("form");
        Integer orderId = (Integer)form.get("orderId");
        String ordernum = (String)form.get("ordernum");
        String orderstate = (String)form.get("orderstate");
        String ordertype = (String)form.get("ordertype");
        String ordertime = (String)form.get("ordertime");
        String usernum = (String)form.get("usernum");
        String passengername = (String)form.get("passengername");
        String passengernum = (String)form.get("passengernum");
        String passengerphonenum = (String)form.get("passengerphonenum");
        String trainNum = (String)form.get("trainNum");
        String trainName = (String)form.get("trainName");
        String traintype = (String)form.get("traintype");
        String trainroomnum = (String)form.get("trainroomnum");
        Integer seatId= (Integer)form.get("seatId");
        String seatnum= (String)form.get("seatnum");
        String seattype = (String)form.get("seattype");
        String seatprice = (String)form.get("seatprice");
        String seatstate = (String)form.get("seatstate");
        String trainbeginplace = (String)form.get("trainbeginplace");
        String trainendplace= (String)form.get("trainendplace");
        String trainbeginday = (String)form.get("trainbeginday");
        String trainendday= (String)form.get("trainendday");
        String trainbegintime = (String)form.get("trainbegintime");
        String trainendtime= (String)form.get("trainendtime");


        trainorder c= null;
        Optional<trainorder> op;
        if(orderId != null) {
            op= orderrepository.findById(orderId);
            if(op.isPresent()) {
                c = op.get();
            }
        }
        if(c == null) {
            c = new trainorder();
        }
        c.setOrder_id(orderId);
        c.setOrder_num(ordernum);
        c.setOrder_state(orderstate);
        c.setOrder_type(ordertype);
        c.setOrder_time(ordertime);
        c.setUser_num(usernum);
        c.setPassenger_name(passengername);
        c.setPassenger_num(passengernum);
        c.setPassenger_num(passengerphonenum);
        c.setTrain_num(trainNum);
        c.setTrain_name(trainName);
        c.setTrain_type(traintype);
        c.setTrain_roomNum(trainroomnum);
        c.setSeat_id(seatId);
        c.setSeat_num(seatnum);
        c.setSeat_type(seattype);
        c.setSeat_price(seatprice);
        c.setSeat_state(seatstate);
        c.setTrain_beginPlace(trainbeginplace);
        c.setTrain_endPlace(trainendplace);
        c.setTrain_beginDay(trainbeginday);
        c.setTrain_endDay(trainendday);
        c.setTrain_beginTime(trainbegintime);
        c.setTrain_endTime(trainendtime);

        orderrepository.save(c);
        return CommonMethod.getReturnData(c.getOrder_id());
    }

    //获取乘车人列表
    @PostMapping("/getpassengerList")
    public DataResponse getpassengerList(@Valid @RequestBody DataRequest dataRequest) {
        String usernum = dataRequest.getString("usernum");
        List list=null;
        list = passengerrepository.findpassengerListByusernum(usernum);
        List dataList = new ArrayList();
        if(list == null || list.size()== 0) {
            CommonMethod.getReturnData(dataList);
        }
        Map m;
        passenger c;
        for(int i = 0; i < list.size();i++) {
            c = (passenger) list.get(i);
            m = new HashMap();
            m.put("passengerId",c.getPassenger_id());
            m.put("usernum",c.getUser_num());
            m.put("passengername",c.getPassenger_name());
            m.put("passengernum",c.getPassenger_num());
            m.put("passengerphonenum",c.getPassenger_phoneNum());
            dataList.add(m);
        }
        return CommonMethod.getReturnData(dataList);
    }

    //获取当前乘车人信息
    @PostMapping("/getpassengerInfo")
    public DataResponse getpassengerInfo(@Valid @RequestBody DataRequest dataRequest) {
        Integer passengerId = dataRequest.getInteger("passengerId");
        passenger c= null;
        Optional<passenger> op;
        if(passengerId != null) {
            op= passengerrepository.findById(passengerId);
            if(op.isPresent()) {
                c = op.get();
            }
        }
        Map form = new HashMap();
        if(c != null) {
            form.put("passengerId",c.getPassenger_id());
            form.put("usernum",c.getUser_num());
            form.put("passengername",c.getPassenger_name());
            form.put("passengernum",c.getPassenger_num());
            form.put("passengerphonenum",c.getPassenger_phoneNum());
        }
        return CommonMethod.getReturnData(form);
    }

    // 乘车人信息修改
    @PostMapping("/passengerSubmit")
    public DataResponse passengerSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Map form = dataRequest.getMap("form");
        Integer passengerId = (Integer)form.get("passengerId");
        String usernum = (String)form.get("usernum");
        String passengername = (String)form.get("passengername");
        String passengernum = (String)form.get("passengernum");
        String passengerphonenum = (String )form.get("passengerphonenum");

        passenger c= null;
        Optional<passenger> op;
        if(passengerId != null) {
            op= passengerrepository.findById(passengerId);
            if(op.isPresent()) {
                c = op.get();
            }
        }
        if(c == null) {
            c = new passenger();
        }
        c.setUser_num(usernum);
        c.setPassenger_name(passengername);
        c.setPassenger_num(passengernum);
        c.setPassenger_phoneNum(passengerphonenum);
        passengerrepository.save(c);
        return CommonMethod.getReturnData(c.getPassenger_id());
    }

    //  乘车人信息删除
    @PostMapping("/passengerDelete")
    public DataResponse passengerDelete(@Valid @RequestBody DataRequest dataRequest) {
        Integer passengerId = dataRequest.getInteger("passengerId");
        passenger c= null;
        Optional<passenger> op;
        if(passengerId != null) {
            op= passengerrepository.findById(passengerId);
            if(op.isPresent()) {
                c = op.get();
            }
        }
        if(c != null) {
            passengerrepository.delete(c);
        }
        return CommonMethod.getReturnMessageOK();
    }

    //获取乘车人列表
    @PostMapping("/getuserList")
    public DataResponse getuserList(@Valid @RequestBody DataRequest dataRequest) {
        List list=null;
        list = userRepository.findAll();
        List dataList = new ArrayList();
        if(list == null || list.size()== 0) {
            CommonMethod.getReturnData(dataList);
        }
        Map m;
        User c;
        for(int i = 0; i < list.size();i++) {
            c = (User) list.get(i);
            m = new HashMap();
            m.put("userId",c.getUserId());
            m.put("username",c.getUserName());
            m.put("usernum",c.getUsernum());
            m.put("usertype",c.getUserType());
            dataList.add(m);
        }
        return CommonMethod.getReturnData(dataList);
    }

    // 乘车人信息修改
    @PostMapping("/usertypechange")
    public DataResponse usertypechange(@Valid @RequestBody DataRequest dataRequest) {
        Integer userId = dataRequest.getInteger("userId");
        String usertype = dataRequest.getString("usertype");


        User c= null;
        Optional<User> op;
        if(userId != null) {
            op= userRepository.findById(userId);
            if(op.isPresent()) {
                c = op.get();
            }
        }
        if(c == null) {
            return CommonMethod.getReturnMessageError("不存在该用户");
        }

        if(Objects.equals(usertype, "2")) {
            UserType FORBIDDEN = userTypeRepository.findByName(EUserType.ROLE_FORBIDDEN);
            c.setUserType(FORBIDDEN);
            userRepository.save(c);
            return CommonMethod.getReturnData(c.getUserId());
        }
        else if(Objects.equals(usertype, "3")) {
            UserType userRole = userTypeRepository.findByName(EUserType.ROLE_USER);
            c.setUserType(userRole);
            userRepository.save(c);
            return CommonMethod.getReturnData(c.getUserId());
        }
        else
        {
            return CommonMethod.getReturnMessageError("修改错误");
        }

    }
}
