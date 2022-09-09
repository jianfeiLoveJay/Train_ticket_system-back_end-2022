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

// origins： 允许可访问的域列表
// maxAge:准备响应前的缓存持续的最大时间（以秒为单位）。
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")

public class CustomerController {
    @Autowired
    UserRepository  userRepository;
    @Autowired
    customerRepository customerrepository;
    @Autowired
    trainRepository trainrepository;
    @Autowired
    seatRepository seatrepository;
    @Autowired
    orderRepository orderrepository;

    //获取学生个人信息
    @PostMapping("/getcustomerInfo")
    public DataResponse getstudentInfo(@Valid @RequestBody DataRequest dataRequest) {
        Integer userId = dataRequest.getInteger("userId");
        List userlist;
        userlist = userRepository.findUserById(userId);
        User user;
        user=(User) userlist.get(0);
        String num=user.getUsernum();
        List customerlist;
        customerlist = customerrepository.findcustomerListByNum(num);
        customer sdu;
        sdu=(customer) customerlist.get(0);
        Map form = new HashMap();
        if(sdu != null) {
            form.put("cusNum",sdu.getCusNum());
            form.put("cusName",sdu.getCusName());
            form.put("cussex",sdu.getCussex());
            form.put("cusemail",sdu.getCusemail());
            form.put("cusphone",sdu.getCusphone());
            form.put("cusaddress",sdu.getCusaddress());
        }
        return CommonMethod.getReturnData(form);
    }

    //提交学生个人信息
    @PostMapping("/customerSubmit")
    public DataResponse studentSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Map form = dataRequest.getMap("form");
        String cusNum = (String) form.get("cusNum");
        String cusName = (String) form.get("cusName");
        String cussex = (String) form.get("cussex");
        String cusemail = (String) form.get("cusemail");
        String cusphone = (String) form.get("cusphone");
        String cusaddress = (String) form.get("cusaddress");
        List customerlist;
        customerlist = customerrepository.findcustomerListByNum(cusNum);
        customer sdu ;
        sdu = (customer) customerlist.get(0);
        sdu.setCusName(cusName);
        sdu.setCussex(cussex);
        sdu.setCusemail(cusemail);
        sdu.setCusphone(cusphone);
        sdu.setCusaddress(cusaddress);
        customerrepository.save(sdu);
        return CommonMethod.getReturnMessageOK();
    }

    //获取当前列车余票
    @PostMapping("/getseatleftList")
    public DataResponse getseatleftList(@Valid @RequestBody DataRequest dataRequest) {
        String trainNum = dataRequest.getString("trainNum");
        String trainroom = dataRequest.getString("trainroom");
        Map m;
        List listT,listY,listE,listS;
        List dataList = new ArrayList();
        for(int i = 1; i < Integer.parseInt(trainroom)+1;i++) {
            listT = seatrepository.findnoreserveseatTListBytrainNumtrainroomnum(trainNum,i+"");
            listY = seatrepository.findnoreserveseatYListBytrainNumtrainroomnum(trainNum,i+"");
            listE = seatrepository.findnoreserveseatEListBytrainNumtrainroomnum(trainNum,i+"");
            listS = seatrepository.findnoreserveseatSListBytrainNumtrainroomnum(trainNum,i+"");
            m = new HashMap();
            m.put("trainroomnum",i);
            m.put("seatnumT",listT.size());
            m.put("seatnumY",listY.size());
            m.put("seatnumE",listE.size());
            m.put("seatnumS",listS.size());
            dataList.add(m);
        }
        return CommonMethod.getReturnData(dataList);
    }

    //直达购票
    @PostMapping("/orderSubmit")
    public DataResponse orderSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Map form = dataRequest.getMap("form");
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
        Integer seatId = (Integer)form.get("seatId");
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

        trainorder c= new trainorder();
        c.setOrder_state(orderstate);
        c.setOrder_type(ordertype);
        c.setOrder_time(ordertime);
        c.setUser_num(usernum);
        c.setPassenger_name(passengername);
        c.setPassenger_num(passengernum);
        c.setPassenger_phoneNum(passengerphonenum);
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

        seat s= null;
        Optional<seat> op;
        if(seatId != null) {
            op= seatrepository.findById(seatId);
            if(op.isPresent()) {
                s = op.get();
            }
            s.setSeat_state("已预订");
            seatrepository.save(s);
        }
        return CommonMethod.getReturnMessageOK();
    }

    //获取换乘列车列表
    @PostMapping("/gettransfertrainList")
    public DataResponse gettransfertrainList(@Valid @RequestBody DataRequest dataRequest) {
        String begin = dataRequest.getString("begin");
        String end = dataRequest.getString("end");
        String time = dataRequest.getString("time");
        String  change = dataRequest.getString("change");
        List list=null;
        if(change.equals("")&&time.equals(""))
            list = trainrepository.findtransfertrainListBybeginendnochangenotime(begin,end);
        if(!change.equals("")&&time.equals(""))
            list = trainrepository.findtransfertrainListBybeginendchange(begin, change, end);
        if(change.equals("")&&!time.equals(""))
            list = trainrepository.findtransfertrainListBybeginendtime(begin, end,time);
        if(!change.equals("")&&!time.equals(""))
            list = trainrepository.findtransfertrainListBybeginendchangetime(begin, change, end,time);

        List dataList = new ArrayList();
        if(list == null || list.size()== 0) {
            CommonMethod.getReturnData(dataList);
        }
        Map m;
        Map<String,Object> c;
        Integer c1id=0;
        Integer c2id=0;
        train c1= null;
        train c2= null;
        for(int i = 0; i < list.size();i++) {
            c = (Map<String,Object>) list.get(i);
            c1id=(Integer)c.get("t1_id");
            c2id=(Integer)c.get("t2_id");

            Optional<train> op1;
            if(c1id != null) {
                op1= trainrepository.findById(c1id);
                if(op1.isPresent()) {
                    c1 = op1.get();
                }
            }
            Optional<train> op2;
            if(c2id != null) {
                op2= trainrepository.findById(c2id);
                if(op2.isPresent()) {
                    c2 = op2.get();
                }
            }
            String dateday1 =c1.getTrain_endDay().substring(0, 4)+c1.getTrain_endDay().substring(5, 7)+c1.getTrain_endDay().substring(8, 10);
            Integer dayint1 =Integer.parseInt(dateday1);
            String datetime1 =c1.getTrain_endTime().substring(0, 2)+c1.getTrain_endTime().substring(3, 5)+c1.getTrain_endTime().substring(6, 8);
            Integer timeint1 =Integer.parseInt(datetime1);
            String dateday2 =c2.getTrain_beginDay().substring(0, 4)+c2.getTrain_beginDay().substring(5, 7)+c2.getTrain_beginDay().substring(8, 10);
            Integer dayint2 =Integer.parseInt(dateday2);
            String datetime2 =c2.getTrain_beginTime().substring(0, 2)+c2.getTrain_beginTime().substring(3, 5)+c2.getTrain_beginTime().substring(6, 8);
            Integer timeint2 =Integer.parseInt(datetime2);


            if((dayint2>dayint1)||(dayint2.equals(dayint1) && timeint2>timeint1)) {
                m = new HashMap();
                m.put("t1trainId", c1.getTrain_id());
                m.put("t1trainNum", c1.getTrain_num());
                m.put("t1trainName", c1.getTrain_name());
                m.put("t1traintype", c1.getTrain_type());
                m.put("t1trainroom", c1.getTrain_room());
                m.put("t1trainroomsize", c1.getTrain_roomSize());
                m.put("t1trainbeginplace", c1.getTrain_beginPlace());
                m.put("t1trainendplace", c1.getTrain_endPlace());
                m.put("t1trainbeginday", c1.getTrain_beginDay());
                m.put("t1trainendday", c1.getTrain_endDay());
                m.put("t1trainbegintime", c1.getTrain_beginTime());
                m.put("t1trainendtime", c1.getTrain_endTime());
                m.put("t2trainId", c2.getTrain_id());
                m.put("t2trainNum", c2.getTrain_num());
                m.put("t2trainName", c2.getTrain_name());
                m.put("t2traintype", c2.getTrain_type());
                m.put("t2trainroom", c2.getTrain_room());
                m.put("t2trainroomsize", c2.getTrain_roomSize());
                m.put("t2trainbeginplace", c2.getTrain_beginPlace());
                m.put("t2trainendplace", c2.getTrain_endPlace());
                m.put("t2trainbeginday", c2.getTrain_beginDay());
                m.put("t2trainendday", c2.getTrain_endDay());
                m.put("t2trainbegintime", c2.getTrain_beginTime());
                m.put("t2trainendtime", c2.getTrain_endTime());
                dataList.add(m);
            }
        }
        return CommonMethod.getReturnData(dataList);
    }

    //获取当前用户订单列表
    @PostMapping("/getorderList")
    public DataResponse getorderList(@Valid @RequestBody DataRequest dataRequest) {
        String usernum = dataRequest.getString("usernum");
        String orderstate = dataRequest.getString("orderstate");
        List list;
        if(usernum.equals("") && orderstate.equals(""))
            list = orderrepository.findAll();
        else if(!usernum.equals("") && orderstate.equals(""))
            list = orderrepository.findorderListByusernum(usernum);
        else if(!orderstate.equals("") && usernum.equals("")  )
            list = orderrepository.findorderListByorderstate(orderstate);
        else
            list = orderrepository.findorderListByusernumorderstate(usernum,orderstate);
        List dataList = new ArrayList();
        if(list == null || list.size()== 0) {
            CommonMethod.getReturnData(dataList);
        }
        Map m;
        trainorder c;
        for(int i = 0; i < list.size();i++) {
            c = (trainorder) list.get(i);
            m = new HashMap();
            m.put("orderId",c.getOrder_id());
            m.put("ordernum",c.getOrder_num());
            m.put("orderstate",c.getOrder_state());
            m.put("ordertype",c.getOrder_type());
            m.put("ordertime",c.getOrder_time());
            m.put("usernum",c.getUser_num());
            m.put("passengername",c.getPassenger_name());
            m.put("passengernum",c.getPassenger_num());
            m.put("passengerphonenum",c.getPassenger_phoneNum());
            m.put("seatId",c.getSeat_id());
            m.put("trainNum",c.getTrain_num());
            m.put("trainName",c.getTrain_name());
            m.put("traintype",c.getTrain_type());
            m.put("trainroomnum",c.getTrain_roomNum());
            m.put("seatnum",c.getSeat_num());
            m.put("seattype",c.getSeat_type());
            m.put("seatprice",c.getSeat_price());
            m.put("seatstate",c.getSeat_state());
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

    //订单状态更改
    @PostMapping("/orderstateSubmit")
    public DataResponse orderstateSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Integer orderId = dataRequest.getInteger("orderId");
        String orderstate =dataRequest.getString("orderstate");
        trainorder c= null;
        Optional<trainorder> op;
        if(orderId != null) {
            op= orderrepository.findById(orderId);
            if(op.isPresent()) {
                c = op.get();
            }
        }
        c.setOrder_state(orderstate);
        Integer seatId=c.getSeat_id();
        seat s= null;
        Optional<seat> op1;
        if(seatId != null) {
            op1= seatrepository.findById(seatId);
            if(op1.isPresent()) {
                s = op1.get();
            }
            s.setSeat_state("未预订");
            seatrepository.save(s);
        }
        orderrepository.save(c);
        return CommonMethod.getReturnMessageOK();
    }

    //获取已取消订单列表
    @PostMapping("/getcanceledorderList")
    public DataResponse getcanceledorderList(@Valid @RequestBody DataRequest dataRequest) {
        String usernum = dataRequest.getString("usernum");
        List list;
        list = orderrepository.findcanceledorderListByusernum(usernum);
        List dataList = new ArrayList();
        if(list == null || list.size()== 0) {
            CommonMethod.getReturnData(dataList);
        }
        Map m;
        trainorder c;
        for(int i = 0; i < list.size();i++) {
            c = (trainorder) list.get(i);
            m = new HashMap();
            m.put("orderId",c.getOrder_id());
            m.put("ordernum",c.getOrder_num());
            m.put("orderstate",c.getOrder_state());
            m.put("ordertype",c.getOrder_type());
            m.put("ordertime",c.getOrder_time());
            m.put("usernum",c.getUser_num());
            m.put("passengername",c.getPassenger_name());
            m.put("passengernum",c.getPassenger_num());
            m.put("passengerphonenum",c.getPassenger_phoneNum());
            m.put("trainNum",c.getTrain_num());
            m.put("trainName",c.getTrain_name());
            m.put("traintype",c.getTrain_type());
            m.put("trainroomnum",c.getTrain_roomNum());
            m.put("seatId",c.getSeat_id());
            m.put("seatnum",c.getSeat_num());
            m.put("seattype",c.getSeat_type());
            m.put("seatprice",c.getSeat_price());
            m.put("seatstate",c.getSeat_state());
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

    //获取已完成订单列表
    @PostMapping("/getcompletedorderList")
    public DataResponse getcompletedorderList(@Valid @RequestBody DataRequest dataRequest) {
        String usernum = dataRequest.getString("usernum");
        String date = dataRequest.getString("date");
        String orderstate = dataRequest.getString("orderstate");

        String nowdateday =date.substring(0, 4)+date.substring(5, 7)+date.substring(8, 10);
        Integer nowdayint =Integer.parseInt(nowdateday);
        String nowdatetime =date.substring(11, 13)+date.substring(14, 16)+date.substring(17, 19);
        Integer nowtimeint =Integer.parseInt(nowdatetime);

        List nouselist;
        nouselist = orderrepository.findorderListByusernumorderstate(usernum,"未使用");
        trainorder c;
        for(int i = 0; i < nouselist.size();i++) {
            c = (trainorder) nouselist.get(i);
            String dateday =c.getTrain_beginDay().substring(0, 4)+c.getTrain_beginDay().substring(5, 7)+c.getTrain_beginDay().substring(8, 10);
            Integer dayint =Integer.parseInt(dateday);
            String datetime =c.getTrain_beginTime().substring(0, 2)+c.getTrain_beginTime().substring(3, 5)+c.getTrain_beginTime().substring(6, 8);
            Integer timeint =Integer.parseInt(datetime);

            if((nowdayint>dayint)||(nowdayint==dayint&&nowtimeint>timeint)) {
                c.setOrder_state(orderstate);
                orderrepository.save(c);
                Integer seatId=c.getSeat_id();
                seat s= null;
                Optional<seat> op;
                if(seatId != null) {
                    op= seatrepository.findById(seatId);
                    if(op.isPresent()) {
                        s = op.get();
                    }
                    s.setSeat_state("未预订");
                    seatrepository.save(s);
                }
            }
        }

        List list;
        list = orderrepository.findorderListByusernumorderstate(usernum,"已完成");
        List dataList = new ArrayList();
        if(list == null || list.size()== 0) {
            CommonMethod.getReturnData(dataList);
        }
        Map m;
        trainorder completedc;
        for(int i = 0; i < list.size();i++) {
            completedc = (trainorder) list.get(i);
            m = new HashMap();
            m.put("orderId",completedc.getOrder_id());
            m.put("ordernum",completedc.getOrder_num());
            m.put("orderstate",completedc.getOrder_state());
            m.put("ordertype",completedc.getOrder_type());
            m.put("ordertime",completedc.getOrder_time());
            m.put("usernum",completedc.getUser_num());
            m.put("passengername",completedc.getPassenger_name());
            m.put("passengernum",completedc.getPassenger_num());
            m.put("passengerphonenum",completedc.getPassenger_phoneNum());
            m.put("trainNum",completedc.getTrain_num());
            m.put("trainName",completedc.getTrain_name());
            m.put("traintype",completedc.getTrain_type());
            m.put("trainroomnum",completedc.getTrain_roomNum());
            m.put("seatId",completedc.getSeat_id());
            m.put("seatnum",completedc.getSeat_num());
            m.put("seattype",completedc.getSeat_type());
            m.put("seatprice",completedc.getSeat_price());
            m.put("seatstate",completedc.getSeat_state());
            m.put("trainbeginplace",completedc.getTrain_beginPlace());
            m.put("trainendplace",completedc.getTrain_endPlace());
            m.put("trainbeginday",completedc.getTrain_beginDay());
            m.put("trainendday",completedc.getTrain_endDay());
            m.put("trainbegintime",completedc.getTrain_beginTime());
            m.put("trainendtime",completedc.getTrain_endTime());
            dataList.add(m);
        }
        return CommonMethod.getReturnData(dataList);
    }

    //删除订单
    @PostMapping("/orderDelete")
    public DataResponse orderDelete(@Valid @RequestBody DataRequest dataRequest) {
        Integer orderId = dataRequest.getInteger("orderId");
        Integer seatId = dataRequest.getInteger("seatId");
        trainorder c= null;
        Optional<trainorder> op;
        if(orderId != null) {
            op= orderrepository.findById(orderId);
            if(op.isPresent()) {
                c = op.get();
            }
        }
        if(c != null) {
            orderrepository.delete(c);
        }

        seat s= null;
        Optional<seat> op1;
        if(seatId != null) {
            op1= seatrepository.findById(seatId);
            if(op1.isPresent()) {
                s = op1.get();
            }
            s.setSeat_state("未预订");
            seatrepository.save(s);
        }
        return CommonMethod.getReturnMessageOK();
    }

    //时间判断
    @PostMapping("/time")
    public DataResponse time(@Valid @RequestBody DataRequest dataRequest) {
        String date = dataRequest.getString("date");
        String beginday = dataRequest.getString("beginday");
        String begintime = dataRequest.getString("begintime");



        String dateday =date.substring(0, 4)+date.substring(5, 7)+date.substring(8, 10);
        Integer dayint =Integer.parseInt(dateday);
        String datetime =date.substring(11, 13)+date.substring(14, 16)+date.substring(17, 19);
        Integer timeint =Integer.parseInt(datetime);


        String begindaystring =beginday.substring(0, 4)+beginday.substring(5, 7)+beginday.substring(8, 10);
        Integer begindayint =Integer.parseInt(begindaystring);
        String begintimestring =begintime.substring(0, 2)+begintime.substring(3, 5)+begintime.substring(6, 8);
        Integer begintimeint =Integer.parseInt(begintimestring);



        if((dayint>begindayint)||(dayint.equals(begindayint) && timeint>begintimeint))
            return CommonMethod.getReturnMessageOK("time");
        else
            return CommonMethod.getReturnMessageOK("begin");

    }
}
