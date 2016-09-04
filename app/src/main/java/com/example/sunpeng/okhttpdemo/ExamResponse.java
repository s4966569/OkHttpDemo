package com.example.sunpeng.okhttpdemo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunpeng on 2016/9/5.
 */
public class ExamResponse implements Serializable {


    private long BaseBeanCreateTime;
    private String code;
    private String debugDesc;
    private String desc;
    private BodyBean body;

    public long getBaseBeanCreateTime() {
        return BaseBeanCreateTime;
    }

    public void setBaseBeanCreateTime(long BaseBeanCreateTime) {
        this.BaseBeanCreateTime = BaseBeanCreateTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDebugDesc() {
        return debugDesc;
    }

    public void setDebugDesc(String debugDesc) {
        this.debugDesc = debugDesc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        private long BaseBeanCreateTime;
        private String totalfficial;
        private String totalscore;
        private String bounstotal;
        private String pofficial;
        private String punofficial;
        private String userGetScore;
        private String bounsscore;
        private String bounsdailylimit;
        private boolean isContainsTeacher;
        private int level;
        private int w;
        private Object checkway;
        private String uRank;
        private int bounsdaily;
        private String userName;
        private String ptotalscore;
        private Object isPass;
        private BounsVoBean bounsVo;
        private List<LeadingVoListBean> leadingVoList;
        private List<BounsVoListBean> bounsVoList;

        public long getBaseBeanCreateTime() {
            return BaseBeanCreateTime;
        }

        public void setBaseBeanCreateTime(long BaseBeanCreateTime) {
            this.BaseBeanCreateTime = BaseBeanCreateTime;
        }

        public String getTotalfficial() {
            return totalfficial;
        }

        public void setTotalfficial(String totalfficial) {
            this.totalfficial = totalfficial;
        }

        public String getTotalscore() {
            return totalscore;
        }

        public void setTotalscore(String totalscore) {
            this.totalscore = totalscore;
        }

        public String getBounstotal() {
            return bounstotal;
        }

        public void setBounstotal(String bounstotal) {
            this.bounstotal = bounstotal;
        }

        public String getPofficial() {
            return pofficial;
        }

        public void setPofficial(String pofficial) {
            this.pofficial = pofficial;
        }

        public String getPunofficial() {
            return punofficial;
        }

        public void setPunofficial(String punofficial) {
            this.punofficial = punofficial;
        }

        public String getUserGetScore() {
            return userGetScore;
        }

        public void setUserGetScore(String userGetScore) {
            this.userGetScore = userGetScore;
        }

        public String getBounsscore() {
            return bounsscore;
        }

        public void setBounsscore(String bounsscore) {
            this.bounsscore = bounsscore;
        }

        public String getBounsdailylimit() {
            return bounsdailylimit;
        }

        public void setBounsdailylimit(String bounsdailylimit) {
            this.bounsdailylimit = bounsdailylimit;
        }

        public boolean isIsContainsTeacher() {
            return isContainsTeacher;
        }

        public void setIsContainsTeacher(boolean isContainsTeacher) {
            this.isContainsTeacher = isContainsTeacher;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getW() {
            return w;
        }

        public void setW(int w) {
            this.w = w;
        }

        public Object getCheckway() {
            return checkway;
        }

        public void setCheckway(Object checkway) {
            this.checkway = checkway;
        }

        public String getURank() {
            return uRank;
        }

        public void setURank(String uRank) {
            this.uRank = uRank;
        }

        public int getBounsdaily() {
            return bounsdaily;
        }

        public void setBounsdaily(int bounsdaily) {
            this.bounsdaily = bounsdaily;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPtotalscore() {
            return ptotalscore;
        }

        public void setPtotalscore(String ptotalscore) {
            this.ptotalscore = ptotalscore;
        }

        public Object getIsPass() {
            return isPass;
        }

        public void setIsPass(Object isPass) {
            this.isPass = isPass;
        }

        public BounsVoBean getBounsVo() {
            return bounsVo;
        }

        public void setBounsVo(BounsVoBean bounsVo) {
            this.bounsVo = bounsVo;
        }

        public List<LeadingVoListBean> getLeadingVoList() {
            return leadingVoList;
        }

        public void setLeadingVoList(List<LeadingVoListBean> leadingVoList) {
            this.leadingVoList = leadingVoList;
        }

        public List<BounsVoListBean> getBounsVoList() {
            return bounsVoList;
        }

        public void setBounsVoList(List<BounsVoListBean> bounsVoList) {
            this.bounsVoList = bounsVoList;
        }

        public static class BounsVoBean {
            private String publishBlog;
            private String publishHomework;
            private String uploadRes;
            private String publishWenda;
            private String timeCourse;
            private String mark;
            private String comment;
            private String followUser;
            private String joinAnswering;
            private String joinActive;
            private String commentEd;
            private String tuiyouEdHomework;
            private String tuiyouEdResource;
            private String downloadEdRes;
            private String bouns1;
            private String bouns2;
            private String bouns3;
            private String bounstotal;
            private String bounsscore;

            public String getPublishBlog() {
                return publishBlog;
            }

            public void setPublishBlog(String publishBlog) {
                this.publishBlog = publishBlog;
            }

            public String getPublishHomework() {
                return publishHomework;
            }

            public void setPublishHomework(String publishHomework) {
                this.publishHomework = publishHomework;
            }

            public String getUploadRes() {
                return uploadRes;
            }

            public void setUploadRes(String uploadRes) {
                this.uploadRes = uploadRes;
            }

            public String getPublishWenda() {
                return publishWenda;
            }

            public void setPublishWenda(String publishWenda) {
                this.publishWenda = publishWenda;
            }

            public String getTimeCourse() {
                return timeCourse;
            }

            public void setTimeCourse(String timeCourse) {
                this.timeCourse = timeCourse;
            }

            public String getMark() {
                return mark;
            }

            public void setMark(String mark) {
                this.mark = mark;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getFollowUser() {
                return followUser;
            }

            public void setFollowUser(String followUser) {
                this.followUser = followUser;
            }

            public String getJoinAnswering() {
                return joinAnswering;
            }

            public void setJoinAnswering(String joinAnswering) {
                this.joinAnswering = joinAnswering;
            }

            public String getJoinActive() {
                return joinActive;
            }

            public void setJoinActive(String joinActive) {
                this.joinActive = joinActive;
            }

            public String getCommentEd() {
                return commentEd;
            }

            public void setCommentEd(String commentEd) {
                this.commentEd = commentEd;
            }

            public String getTuiyouEdHomework() {
                return tuiyouEdHomework;
            }

            public void setTuiyouEdHomework(String tuiyouEdHomework) {
                this.tuiyouEdHomework = tuiyouEdHomework;
            }

            public String getTuiyouEdResource() {
                return tuiyouEdResource;
            }

            public void setTuiyouEdResource(String tuiyouEdResource) {
                this.tuiyouEdResource = tuiyouEdResource;
            }

            public String getDownloadEdRes() {
                return downloadEdRes;
            }

            public void setDownloadEdRes(String downloadEdRes) {
                this.downloadEdRes = downloadEdRes;
            }

            public String getBouns1() {
                return bouns1;
            }

            public void setBouns1(String bouns1) {
                this.bouns1 = bouns1;
            }

            public String getBouns2() {
                return bouns2;
            }

            public void setBouns2(String bouns2) {
                this.bouns2 = bouns2;
            }

            public String getBouns3() {
                return bouns3;
            }

            public void setBouns3(String bouns3) {
                this.bouns3 = bouns3;
            }

            public String getBounstotal() {
                return bounstotal;
            }

            public void setBounstotal(String bounstotal) {
                this.bounstotal = bounstotal;
            }

            public String getBounsscore() {
                return bounsscore;
            }

            public void setBounsscore(String bounsscore) {
                this.bounsscore = bounsscore;
            }
        }

        public static class LeadingVoListBean {
            private int id;
            private String name;
            private int isfinish;
            private String userscore;
            private String totalscore;
            private Object enddate;
            private List<ToolExamineVoListBean> toolExamineVoList;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getIsfinish() {
                return isfinish;
            }

            public void setIsfinish(int isfinish) {
                this.isfinish = isfinish;
            }

            public String getUserscore() {
                return userscore;
            }

            public void setUserscore(String userscore) {
                this.userscore = userscore;
            }

            public String getTotalscore() {
                return totalscore;
            }

            public void setTotalscore(String totalscore) {
                this.totalscore = totalscore;
            }

            public Object getEnddate() {
                return enddate;
            }

            public void setEnddate(Object enddate) {
                this.enddate = enddate;
            }

            public List<ToolExamineVoListBean> getToolExamineVoList() {
                return toolExamineVoList;
            }

            public void setToolExamineVoList(List<ToolExamineVoListBean> toolExamineVoList) {
                this.toolExamineVoList = toolExamineVoList;
            }

            public static class ToolExamineVoListBean {
                private int toolid;
                private String name;
                private int finishnum;
                private int totalnum;
                private String userscore;
                private String totalscore;
                private int isneedmark;

                public int getToolid() {
                    return toolid;
                }

                public void setToolid(int toolid) {
                    this.toolid = toolid;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getFinishnum() {
                    return finishnum;
                }

                public void setFinishnum(int finishnum) {
                    this.finishnum = finishnum;
                }

                public int getTotalnum() {
                    return totalnum;
                }

                public void setTotalnum(int totalnum) {
                    this.totalnum = totalnum;
                }

                public String getUserscore() {
                    return userscore;
                }

                public void setUserscore(String userscore) {
                    this.userscore = userscore;
                }

                public String getTotalscore() {
                    return totalscore;
                }

                public void setTotalscore(String totalscore) {
                    this.totalscore = totalscore;
                }

                public int getIsneedmark() {
                    return isneedmark;
                }

                public void setIsneedmark(int isneedmark) {
                    this.isneedmark = isneedmark;
                }
            }
        }

        public static class BounsVoListBean {
            private int id;
            private String name;
            private int isfinish;
            private String userscore;
            private String totalscore;
            private Object enddate;
            private int toolid;
            private int finishnum;
            private int totalnum;
            private int isneedmark;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getIsfinish() {
                return isfinish;
            }

            public void setIsfinish(int isfinish) {
                this.isfinish = isfinish;
            }

            public String getUserscore() {
                return userscore;
            }

            public void setUserscore(String userscore) {
                this.userscore = userscore;
            }

            public String getTotalscore() {
                return totalscore;
            }

            public void setTotalscore(String totalscore) {
                this.totalscore = totalscore;
            }

            public Object getEnddate() {
                return enddate;
            }

            public void setEnddate(Object enddate) {
                this.enddate = enddate;
            }

            public int getToolid() {
                return toolid;
            }

            public void setToolid(int toolid) {
                this.toolid = toolid;
            }

            public int getFinishnum() {
                return finishnum;
            }

            public void setFinishnum(int finishnum) {
                this.finishnum = finishnum;
            }

            public int getTotalnum() {
                return totalnum;
            }

            public void setTotalnum(int totalnum) {
                this.totalnum = totalnum;
            }

            public int getIsneedmark() {
                return isneedmark;
            }

            public void setIsneedmark(int isneedmark) {
                this.isneedmark = isneedmark;
            }
        }
    }
}
