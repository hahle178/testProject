package com.chrtc.excelTest.excelTest.domain.kettle;import java.io.Serializable;import java.math.BigDecimal;import java.util.Date;import javax.validation.constraints.Size;import javax.validation.constraints.NotNull;import com.fasterxml.jackson.annotation.JsonFormat;import org.apache.ibatis.type.Alias;import com.chrtc.common.base.domain.BaseEntity;/** * Created by AUTO on 2018-08-02 10:22:05. * k_job */public class Kjob extends BaseEntity {    /**主键*/    @Size(max=36)    private String id;    /**创建人名称*/    @Size(max=50)    private String createName;    /**创建人登录名称*/    @Size(max=50)    private String createBy;    /**创建日期*/   @JsonFormat(pattern = "yyyy-MM-dd")    private Date createDate;    /**更新人名称*/    @Size(max=50)    private String updateName;    /**更新人登录名称*/    @Size(max=50)    private String updateBy;    /**更新日期*/   @JsonFormat(pattern = "yyyy-MM-dd")    private Date updateDate;    /**版本号*/    private Integer versionNum;    /**组织编号*/    @Size(max=50)    private String sysOrgCode;    /**删除标志*/    @Size(max=1)    private String delFlag;    /**备用字段A*/    @Size(max=60)    private String obligateA;    /**备用字段B*/    @Size(max=60)    private String obligateB;    /**备用字段C*/    @Size(max=60)    private String obligateC;    /**备用字段D*/    @Size(max=60)    private String obligateD;    /**备用字段E*/    @Size(max=60)    private String obligateE;    /**作业ID*/    @Size(max=50)    private String jobId;    /**作业名称*/    @Size(max=50)    private String jobName;    /**任务描述*/    @Size(max=50)    private String jobDescription;    /**1:数据库资源库；2:上传的文件*/    @Size(max=50)    private String jobType;    /**作业保存路径（可以是资源库中的路径也可以是服务器中保存作业文件的路径）*/    @Size(max=50)    private String jobPath;    /**作业的资源库ID*/    @Size(max=50)    private String jobRepositoryId;    /**定时策略（外键ID）*/    @Size(max=50)    private String jobQuartz;    /**作业执行记录（外键ID）*/    @Size(max=50)    private String jobRecord;    /**日志级别(basic，detail，error，debug，minimal，rowlevel）*/    @Size(max=50)    private String jobLogLevel;    /**状态（1：正在运行；2：已停止）*/    @Size(max=50)    private String jobStatus;    /**添加时间*/    @Size(max=50)    private String addTime;    /**添加者*/    @Size(max=50)    private String addUser;    /**编辑时间*/    @Size(max=50)    private String editTime;    /**编辑者*/    @Size(max=50)    private String editUser;    public String getId(){    return this.id;    }    public void setId(String id){    this.id = id;    }    public String getCreateName(){    return this.createName;    }    public void setCreateName(String createName){    this.createName = createName;    }    public String getCreateBy(){    return this.createBy;    }    public void setCreateBy(String createBy){    this.createBy = createBy;    }    public Date getCreateDate(){    return this.createDate;    }    public void setCreateDate(Date createDate){    this.createDate = createDate;    }    public String getUpdateName(){    return this.updateName;    }    public void setUpdateName(String updateName){    this.updateName = updateName;    }    public String getUpdateBy(){    return this.updateBy;    }    public void setUpdateBy(String updateBy){    this.updateBy = updateBy;    }    public Date getUpdateDate(){    return this.updateDate;    }    public void setUpdateDate(Date updateDate){    this.updateDate = updateDate;    }    public Integer getVersionNum(){    return this.versionNum;    }    public void setVersionNum(Integer versionNum){    this.versionNum = versionNum;    }    public String getSysOrgCode(){    return this.sysOrgCode;    }    public void setSysOrgCode(String sysOrgCode){    this.sysOrgCode = sysOrgCode;    }    public String getDelFlag(){    return this.delFlag;    }    public void setDelFlag(String delFlag){    this.delFlag = delFlag;    }    public String getObligateA(){    return this.obligateA;    }    public void setObligateA(String obligateA){    this.obligateA = obligateA;    }    public String getObligateB(){    return this.obligateB;    }    public void setObligateB(String obligateB){    this.obligateB = obligateB;    }    public String getObligateC(){    return this.obligateC;    }    public void setObligateC(String obligateC){    this.obligateC = obligateC;    }    public String getObligateD(){    return this.obligateD;    }    public void setObligateD(String obligateD){    this.obligateD = obligateD;    }    public String getObligateE(){    return this.obligateE;    }    public void setObligateE(String obligateE){    this.obligateE = obligateE;    }    public String getJobId(){    return this.jobId;    }    public void setJobId(String jobId){    this.jobId = jobId;    }    public String getJobName(){    return this.jobName;    }    public void setJobName(String jobName){    this.jobName = jobName;    }    public String getJobDescription(){    return this.jobDescription;    }    public void setJobDescription(String jobDescription){    this.jobDescription = jobDescription;    }    public String getJobType(){    return this.jobType;    }    public void setJobType(String jobType){    this.jobType = jobType;    }    public String getJobPath(){    return this.jobPath;    }    public void setJobPath(String jobPath){    this.jobPath = jobPath;    }    public String getJobRepositoryId(){    return this.jobRepositoryId;    }    public void setJobRepositoryId(String jobRepositoryId){    this.jobRepositoryId = jobRepositoryId;    }    public String getJobQuartz(){    return this.jobQuartz;    }    public void setJobQuartz(String jobQuartz){    this.jobQuartz = jobQuartz;    }    public String getJobRecord(){    return this.jobRecord;    }    public void setJobRecord(String jobRecord){    this.jobRecord = jobRecord;    }    public String getJobLogLevel(){    return this.jobLogLevel;    }    public void setJobLogLevel(String jobLogLevel){    this.jobLogLevel = jobLogLevel;    }    public String getJobStatus(){    return this.jobStatus;    }    public void setJobStatus(String jobStatus){    this.jobStatus = jobStatus;    }    public String getAddTime(){    return this.addTime;    }    public void setAddTime(String addTime){    this.addTime = addTime;    }    public String getAddUser(){    return this.addUser;    }    public void setAddUser(String addUser){    this.addUser = addUser;    }    public String getEditTime(){    return this.editTime;    }    public void setEditTime(String editTime){    this.editTime = editTime;    }    public String getEditUser(){    return this.editUser;    }    public void setEditUser(String editUser){    this.editUser = editUser;    }	}