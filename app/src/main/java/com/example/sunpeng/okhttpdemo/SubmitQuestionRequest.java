package com.example.sunpeng.okhttpdemo;

import java.io.Serializable;

/**
 * Created by sunpeng on 2016/9/4.
 */
public class SubmitQuestionRequest implements Serializable{
    private String osType;
    private String pcode;
    private String token;
    private String version;
    private String answers="{\"chapterId\":\"64109\",\"ptype\":1,\"paperDetails\":[{\"answer\":[{\"answer\":[],\"qid\":\"2740232\"}],\"children\":[{\"id\":\"92468\",\"qid\":\"2740232\",\"costtime\":5,\"ptid\":146827,\"status\":3,\"uid\":168,\"answer\":[]}],\"costtime\":0,\"ptid\":146826,\"qid\":\"2740231\",\"id\":\"92469\",\"status\":3,\"uid\":168},{\"answer\":[{\"answer\":[],\"qid\":\"2740264\"}],\"children\":[{\"id\":\"92470\",\"qid\":\"2740264\",\"costtime\":0,\"ptid\":146829,\"status\":3,\"uid\":168,\"answer\":[]}],\"costtime\":0,\"ptid\":146828,\"qid\":\"2740242\",\"id\":\"92471\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146830,\"qid\":\"2740229\",\"id\":\"92472\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146831,\"qid\":\"2745407\",\"id\":\"92473\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146832,\"qid\":\"2745408\",\"id\":\"92474\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146833,\"qid\":\"2745445\",\"id\":\"92475\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146834,\"qid\":\"2740251\",\"id\":\"92476\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146835,\"qid\":\"2745411\",\"id\":\"92477\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146836,\"qid\":\"2745412\",\"id\":\"92478\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146837,\"qid\":\"2740244\",\"id\":\"92478\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146838,\"qid\":\"2745413\",\"id\":\"92478\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146839,\"qid\":\"2745414\",\"id\":\"92478\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146840,\"qid\":\"2740245\",\"id\":\"92478\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146841,\"qid\":\"2740263\",\"id\":\"92479\",\"status\":3,\"uid\":168},{\"answer\":[{\"answer\":[],\"qid\":\"2740287\"},{\"answer\":[],\"qid\":\"2740288\"},{\"answer\":[],\"qid\":\"2740289\"},{\"answer\":[],\"qid\":\"2740290\"}],\"children\":[{\"id\":\"92480\",\"qid\":\"2740287\",\"costtime\":0,\"ptid\":146843,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92481\",\"qid\":\"2740288\",\"costtime\":0,\"ptid\":146844,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92482\",\"qid\":\"2740289\",\"costtime\":0,\"ptid\":146845,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92483\",\"qid\":\"2740290\",\"costtime\":0,\"ptid\":146846,\"status\":3,\"uid\":168,\"answer\":[]}],\"costtime\":0,\"ptid\":146842,\"qid\":\"2740246\",\"id\":\"92484\",\"status\":3,\"uid\":168},{\"answer\":[{\"answer\":[],\"qid\":\"2745388\"},{\"answer\":[],\"qid\":\"2745389\"},{\"answer\":[],\"qid\":\"2745390\"},{\"answer\":[],\"qid\":\"2745391\"},{\"answer\":[],\"qid\":\"2745392\"},{\"answer\":[],\"qid\":\"2745393\"},{\"answer\":[],\"qid\":\"2745394\"},{\"answer\":[],\"qid\":\"2745395\"}],\"children\":[{\"id\":\"92485\",\"qid\":\"2745388\",\"costtime\":0,\"ptid\":146848,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92486\",\"qid\":\"2745389\",\"costtime\":0,\"ptid\":146849,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92487\",\"qid\":\"2745390\",\"costtime\":0,\"ptid\":146850,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92488\",\"qid\":\"2745391\",\"costtime\":0,\"ptid\":146851,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92489\",\"qid\":\"2745392\",\"costtime\":0,\"ptid\":146852,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92490\",\"qid\":\"2745393\",\"costtime\":0,\"ptid\":146853,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92491\",\"qid\":\"2745394\",\"costtime\":0,\"ptid\":146854,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92492\",\"qid\":\"2745395\",\"costtime\":0,\"ptid\":146855,\"status\":3,\"uid\":168,\"answer\":[]}],\"costtime\":0,\"ptid\":146847,\"qid\":\"2740252\",\"id\":\"92493\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146856,\"qid\":\"2745409\",\"id\":\"92494\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146857,\"qid\":\"2745410\",\"id\":\"92495\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146858,\"qid\":\"2745416\",\"id\":\"92496\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146859,\"qid\":\"2745418\",\"id\":\"92497\",\"status\":3,\"uid\":168},{\"answer\":[{\"answer\":[],\"qid\":\"2745421\"},{\"answer\":[],\"qid\":\"2745422\"}],\"children\":[{\"id\":\"92498\",\"qid\":\"2745421\",\"costtime\":0,\"ptid\":146861,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92499\",\"qid\":\"2745422\",\"costtime\":0,\"ptid\":146862,\"status\":3,\"uid\":168,\"answer\":[]}],\"costtime\":0,\"ptid\":146860,\"qid\":\"2745420\",\"id\":\"92500\",\"status\":3,\"uid\":168},{\"answer\":[{\"answer\":[],\"qid\":\"2745424\"},{\"answer\":[],\"qid\":\"2745425\"},{\"answer\":[],\"qid\":\"2745426\"}],\"children\":[{\"id\":\"92501\",\"qid\":\"2745424\",\"costtime\":0,\"ptid\":146864,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92502\",\"qid\":\"2745425\",\"costtime\":0,\"ptid\":146865,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92503\",\"qid\":\"2745426\",\"costtime\":0,\"ptid\":146866,\"status\":3,\"uid\":168,\"answer\":[]}],\"costtime\":0,\"ptid\":146863,\"qid\":\"2745423\",\"id\":\"92504\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146867,\"qid\":\"2740262\",\"id\":\"92505\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146868,\"qid\":\"2740261\",\"id\":\"92506\",\"status\":3,\"uid\":168},{\"answer\":[{\"answer\":[],\"qid\":\"2745404\"},{\"answer\":[],\"qid\":\"2745405\"},{\"answer\":[],\"qid\":\"2745406\"}],\"children\":[{\"id\":\"92507\",\"qid\":\"2745404\",\"costtime\":0,\"ptid\":146870,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92508\",\"qid\":\"2745405\",\"costtime\":0,\"ptid\":146871,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92509\",\"qid\":\"2745406\",\"costtime\":0,\"ptid\":146872,\"status\":3,\"uid\":168,\"answer\":[]}],\"costtime\":0,\"ptid\":146869,\"qid\":\"2745400\",\"id\":\"92510\",\"status\":3,\"uid\":168}],\"paperStatus\":{\"begintime\":1472998490014,\"endtime\":1472998495180,\"costtime\":5,\"ppid\":19390,\"id\":\"16405\",\"status\":\"2\",\"tid\":0,\"uid\":168}}";

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
