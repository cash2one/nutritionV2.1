package com.jumper.weight.nutrition.food.vo;

import java.io.Serializable;

import com.jumper.weight.common.util.FunctionUtils;


/**
 * @Description 营养分析
 * @author fangxilin
 * @date 2017-4-10
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class VONutritionAnalyze implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**能量摄入量*/
	private Double energyIntake = 0D;
	/**能量推荐量*/
	private Integer energyRecommend = 0;
	/**能量摄入量占比*/
	private Double energyPer = 0D;
	/**能量摄入量状态 0：不足，1：达标，2：偏高*/
	private Integer energyStatus = 0;
	/**蛋白质摄入量*/
	private Double proteinIntake = 0D;
	/**蛋白质推荐量*/
	private Double[] proteinRecommend;
	/**蛋白质摄入量占比*/
	private Double[] proteinPer = {0D, 0D};
	/**蛋白质摄入量状态 0：不足，1：达标，2：偏高*/
	private Integer proteinStatus = 0;
	/**脂肪摄入量*/
	private Double fatIntake = 0D;
	/**脂肪推荐量*/
	private Double[] fatRecommend;
	/**脂肪摄入量占比*/
	private Double[] fatPer = {0D, 0D};
	/**脂肪摄入量状态 0：不足，1：达标，2：偏高*/
	private Integer fatStatus = 0;
	/**碳水化合物摄入量*/
	private Double carbonIntake = 0D;
	/**碳水化合物推荐量*/
	private Double[] carbonRecommend;
	/**碳水化合物摄摄入量占比*/
	private Double[] carbonPer = {0D, 0D};
	/**碳水化合物摄入量状态 0：不足，1：达标，2：偏高*/
	private Integer carbonStatus = 0;
	
	/**钙摄入量*/
	private Double caIntake = 0D;
	/**钙推荐量(单位：mg)*/
	private Integer caRecommend = 800;
	/**钙摄入量占比*/
	private Double caPer = 0D;
	/**钙摄入量状态 0：不足，1：达标，2：偏高*/
	private Integer caStatus = 0;
	/**铁摄入量*/
	private Double feIntake = 0D;
	/**铁推荐量(单位：mg)*/
	private Integer feRecommend = 20;
	/**铁摄入量占比*/
	private Double fePer = 0D;
	/**铁摄入量状态 0：不足，1：达标，2：偏高*/
	private Integer feStatus = 0;
	/**锌摄入量*/
	private Double znIntake = 0D;
	/**锌推荐量(单位：mg)*/
	private Double znRecommend = 9.5;
	/**锌摄入量占比*/
	private Double znPer = 0D;
	/**锌摄入量状态 0：不足，1：达标，2：偏高*/
	private Integer znStatus = 0;
	/**硒摄入量*/
	private Double seIntake = 0D;
	/**硒推荐量(单位：mg)*/
	private Integer seRecommend = 65;
	/**硒摄入量占比*/
	private Double sePer = 0D;
	/**硒摄入量状态 0：不足，1：达标，2：偏高*/
	private Integer seStatus = 0;
	/**铜摄入量*/
	private Double cuIntake = 0D;
	/**铜推荐量(单位：mg)*/
	private Double cuRecommend = 0.9;
	/**铜摄入量占比*/
	private Double cuPer = 0D;
	/**铜摄入量状态 0：不足，1：达标，2：偏高*/
	private Integer cuStatus = 0;
	/**钠摄入量*/
	private Double naIntake = 0D;
	/**钠推荐量(单位：mg)*/
	private Integer naRecommend = 1500;
	/**钠摄入量占比*/
	private Double naPer = 0D;
	/**钠摄入量状态 0：不足，1：达标，2：偏高*/
	private Integer naStatus = 0;
	/**碘摄入量*/
	private Double iIntake = 0D;
	/**碘推荐量(单位：mg)*/
	private Integer iRecommend = 230;
	/**碘摄入量占比*/
	private Double iPer = 0D;
	/**碘摄入量状态 0：不足，1：达标，2：偏高*/
	private Integer iStatus = 0;
	/**磷摄入量*/
	private Double pIntake = 0D;
	/**磷推荐量(单位：mg)*/
	private Integer pRecommend = 720;
	/**磷摄入量占比*/
	private Double pPer = 0D;
	/**磷摄入量状态 0：不足，1：达标，2：偏高*/
	private Integer pStatus = 0;
	/**钾摄入量*/
	private Double kIntake = 0D;
	/**钾推荐量(单位：mg)*/
	private Integer kRecommend = 2000;
	/**钾摄入量占比*/
	private Double kPer = 0D;
	/**钾摄入量状态 0：不足，1：达标，2：偏高*/
	private Integer kStatus = 0;
	/**镁摄入量*/
	private Double mgIntake = 0D;
	/**镁推荐量(单位：mg)*/
	private Integer mgRecommend = 370;
	/**镁摄入量占比*/
	private Double mgPer = 0D;
	/**镁摄入量状态 0：不足，1：达标，2：偏高*/
	private Integer mgStatus = 0;
	/**锰摄入量*/
	private Double mnIntake = 0D;
	/**锰推荐量(单位：mg)*/
	private Double mnRecommend = 4.9;
	/**锰摄入量占比*/
	private Double mnPer = 0D;
	/**锰摄入量状态 0：不足，1：达标，2：偏高*/
	private Integer mnStatus = 0;
	/**维生素A摄入量*/
	private Double vaIntake = 0D;
	/**维生素A推荐量(单位：ugRAE)*/
	private Integer vaRecommend = 700;
	/**维生素A摄入量占比*/
	private Double vaPer = 0D;
	/**维生素A摄入量状态 0：不足，1：达标，2：偏高*/
	private Integer vaStatus = 0;
	/**维生素B6摄入量*/
	private Double vbIntake = 0D;
	/**维生素B6推荐量(单位：mg)*/
	private Double vbRecommend = 2.2;
	/**维生素B6摄入量占比*/
	private Double vbPer = 0D;
	/**维生素B6摄入量状态 0：不足，1：达标，2：偏高*/
	private Integer vbStatus = 0;
	/**维生素C摄入量*/
	private Double vcIntake = 0D;
	/**维生素C推荐量(单位：mg)*/
	private Integer vcRecommend = 100;
	/**维生素C摄入量占比*/
	private Double vcPer = 0D;
	/**维生素C摄入量状态 0：不足，1：达标，2：偏高*/
	private Integer vcStatus = 0;
	/**优质蛋白摄入量*/
	private Double highQualityProIntake = 0D;
	/**非优质蛋白摄入量*/
	private Double nonPrimeProIntake = 0D;
	
	public Double getEnergyIntake() {
		return energyIntake;
	}
	public void setEnergyIntake(Double energyIntake) {
		this.energyIntake = energyIntake;
	}
	public Integer getEnergyRecommend() {
		return energyRecommend;
	}
	public void setEnergyRecommend(Integer energyRecommend) {
		this.energyRecommend = energyRecommend;
	}
	public Double getEnergyPer() {
		if (energyRecommend == 0) {
			return energyPer;
		}
		energyPer = FunctionUtils.setDecimal(energyIntake / energyRecommend * 100, 1);
		return energyPer;
	}
	public void setEnergyPer(Double energyPer) {
		this.energyPer = energyPer;
	}
	public Double getProteinIntake() {
		return proteinIntake;
	}
	public void setProteinIntake(Double proteinIntake) {
		this.proteinIntake = proteinIntake;
	}
	public Double[] getProteinRecommend() {
		//蛋白质推荐量占能量比：12%-15%（4卡 == 1g）
		double min = FunctionUtils.setDecimal(energyRecommend * 0.12 / 4, 1);
		double max = FunctionUtils.setDecimal(energyRecommend * 0.15 / 4, 1);
		proteinRecommend = new Double[]{min, max};
		return proteinRecommend;
	}
	public void setProteinRecommend(Double[] proteinRecommend) {
		this.proteinRecommend = proteinRecommend;
	}
	public Double[] getProteinPer() {
		if (getProteinRecommend()[0] == 0) {
			return proteinPer;
		}
		double min = FunctionUtils.setDecimal(proteinIntake / getProteinRecommend()[0] * 100, 1);
		double max = FunctionUtils.setDecimal(proteinIntake / getProteinRecommend()[1] * 100, 1);
		proteinPer = new Double[]{min, max};
		return proteinPer;
	}
	public void setProteinPer(Double[] proteinPer) {
		this.proteinPer = proteinPer;
	}
	public Double getFatIntake() {
		return fatIntake;
	}
	public void setFatIntake(Double fatIntake) {
		this.fatIntake = fatIntake;
	}
	public Double[] getFatRecommend() {
		//脂肪推荐量占能量比：25%-30%
		double min = FunctionUtils.setDecimal(energyRecommend * 0.25 / 9, 1);
		double max = FunctionUtils.setDecimal(energyRecommend * 0.30 / 9, 1);
		fatRecommend = new Double[]{min, max};
		return fatRecommend;
	}
	public void setFatRecommend(Double[] fatRecommend) {
		this.fatRecommend = fatRecommend;
	}
	public Double[] getFatPer() {
		if (getFatRecommend()[0] == 0) {
			return proteinPer;
		}
		double min = FunctionUtils.setDecimal(fatIntake / getFatRecommend()[0] * 100, 1);
		double max = FunctionUtils.setDecimal(fatIntake / getFatRecommend()[1] * 100, 1);
		fatPer = new Double[]{min, max};
		return fatPer;
	}
	public void setFatPer(Double[] fatPer) {
		this.fatPer = fatPer;
	}
	public Double getCarbonIntake() {
		return carbonIntake;
	}
	public void setCarbonIntake(Double carbonIntake) {
		this.carbonIntake = carbonIntake;
	}
	public Double[] getCarbonRecommend() {
		//碳水化合物推荐量占能量比55%-60%
		double min = FunctionUtils.setDecimal(energyRecommend * 0.55 / 4, 1);
		double max = FunctionUtils.setDecimal(energyRecommend * 0.60 / 4, 1);
		carbonRecommend = new Double[]{min, max};
		return carbonRecommend;
	}
	public void setCarbonRecommend(Double[] carbonRecommend) {
		this.carbonRecommend = carbonRecommend;
	}
	public Double[] getCarbonPer() {
		if (getCarbonRecommend()[0] == 0) {
			return proteinPer;
		}
		double min = FunctionUtils.setDecimal(carbonIntake / getCarbonRecommend()[0] * 100, 1);
		double max = FunctionUtils.setDecimal(carbonIntake / getCarbonRecommend()[1] * 100, 1);
		carbonPer = new Double[]{min, max};
		return carbonPer;
	}
	public void setCarbonPer(Double[] carbonPer) {
		this.carbonPer = carbonPer;
	}
	public Double getNaIntake() {
		return naIntake;
	}
	public void setNaIntake(Double naIntake) {
		this.naIntake = naIntake;
	}
	public Integer getNaRecommend() {
		return naRecommend;
	}
	public void setNaRecommend(Integer naRecommend) {
		this.naRecommend = naRecommend;
	}
	public Double getNaPer() {
		naPer = FunctionUtils.setDecimal(naIntake / naRecommend * 100, 1);
		return naPer;
	}
	public void setNaPer(Double naPer) {
		this.naPer = naPer;
	}
	public Double getCaIntake() {
		return caIntake;
	}
	public void setCaIntake(Double caIntake) {
		this.caIntake = caIntake;
	}
	public Integer getCaRecommend() {
		return caRecommend;
	}
	public void setCaRecommend(Integer caRecommend) {
		this.caRecommend = caRecommend;
	}
	public Double getCaPer() {
		caPer = FunctionUtils.setDecimal(caIntake / caRecommend * 100, 1);
		return caPer;
	}
	public void setCaPer(Double caPer) {
		this.caPer = caPer;
	}
	public Double getFeIntake() {
		return feIntake;
	}
	public void setFeIntake(Double feIntake) {
		this.feIntake = feIntake;
	}
	public Integer getFeRecommend() {
		return feRecommend;
	}
	public void setFeRecommend(Integer feRecommend) {
		this.feRecommend = feRecommend;
	}
	public Double getFePer() {
		fePer = FunctionUtils.setDecimal(feIntake / feRecommend * 100, 1);
		return fePer;
	}
	public void setFePer(Double fePer) {
		this.fePer = fePer;
	}
	public Double getZnIntake() {
		return znIntake;
	}
	public void setZnIntake(Double znIntake) {
		this.znIntake = znIntake;
	}
	public Double getZnRecommend() {
		return znRecommend;
	}
	public void setZnRecommend(Double znRecommend) {
		this.znRecommend = znRecommend;
	}
	public Double getZnPer() {
		znPer = FunctionUtils.setDecimal(znIntake / znRecommend * 100, 1);
		return znPer;
	}
	public void setZnPer(Double znPer) {
		this.znPer = znPer;
	}
	public Double getpIntake() {
		return pIntake;
	}
	public void setpIntake(Double pIntake) {
		this.pIntake = pIntake;
	}
	public Integer getpRecommend() {
		return pRecommend;
	}
	public void setpRecommend(Integer pRecommend) {
		this.pRecommend = pRecommend;
	}
	public Double getpPer() {
		pPer = FunctionUtils.setDecimal(pIntake / pRecommend * 100, 1);
		return pPer;
	}
	public void setpPer(Double pPer) {
		this.pPer = pPer;
	}
	public Double getkIntake() {
		return kIntake;
	}
	public void setkIntake(Double kIntake) {
		this.kIntake = kIntake;
	}
	public Integer getkRecommend() {
		return kRecommend;
	}
	public void setkRecommend(Integer kRecommend) {
		this.kRecommend = kRecommend;
	}
	public Double getkPer() {
		kPer = FunctionUtils.setDecimal(kIntake / kRecommend * 100, 1);
		return kPer;
	}
	public void setkPer(Double kPer) {
		this.kPer = kPer;
	}
	public Double getMgIntake() {
		return mgIntake;
	}
	public void setMgIntake(Double mgIntake) {
		this.mgIntake = mgIntake;
	}
	public Integer getMgRecommend() {
		return mgRecommend;
	}
	public void setMgRecommend(Integer mgRecommend) {
		this.mgRecommend = mgRecommend;
	}
	public Double getMgPer() {
		mgPer = FunctionUtils.setDecimal(mgIntake / mgRecommend * 100, 1);
		return mgPer;
	}
	public void setMgPer(Double mgPer) {
		this.mgPer = mgPer;
	}
	public Double getCuIntake() {
		return cuIntake;
	}
	public void setCuIntake(Double cuIntake) {
		this.cuIntake = cuIntake;
	}
	public Double getCuRecommend() {
		return cuRecommend;
	}
	public void setCuRecommend(Double cuRecommend) {
		this.cuRecommend = cuRecommend;
	}
	public Double getCuPer() {
		cuPer = FunctionUtils.setDecimal(cuIntake / cuRecommend * 100, 1);
		return cuPer;
	}
	public void setCuPer(Double cuPer) {
		this.cuPer = cuPer;
	}
	public Double getMnIntake() {
		return mnIntake;
	}
	public void setMnIntake(Double mnIntake) {
		this.mnIntake = mnIntake;
	}
	public Double getMnRecommend() {
		return mnRecommend;
	}
	public void setMnRecommend(Double mnRecommend) {
		this.mnRecommend = mnRecommend;
	}
	public Double getMnPer() {
		mnPer = FunctionUtils.setDecimal(mnIntake / mnRecommend * 100, 1);
		return mnPer;
	}
	public void setMnPer(Double mnPer) {
		this.mnPer = mnPer;
	}
	public Double getSeIntake() {
		return seIntake;
	}
	public void setSeIntake(Double seIntake) {
		this.seIntake = seIntake;
	}
	public Integer getSeRecommend() {
		return seRecommend;
	}
	public void setSeRecommend(Integer seRecommend) {
		this.seRecommend = seRecommend;
	}
	public Double getSePer() {
		sePer = FunctionUtils.setDecimal(seIntake / seRecommend * 100, 1);
		return sePer;
	}
	public void setSePer(Double sePer) {
		this.sePer = sePer;
	}
	public Double getiIntake() {
		return iIntake;
	}
	public void setiIntake(Double iIntake) {
		this.iIntake = iIntake;
	}
	public Integer getiRecommend() {
		return iRecommend;
	}
	public void setiRecommend(Integer iRecommend) {
		this.iRecommend = iRecommend;
	}
	public Double getiPer() {
		iPer = FunctionUtils.setDecimal(iIntake / iRecommend * 100, 1);
		return iPer;
	}
	public void setiPer(Double iPer) {
		this.iPer = iPer;
	}
	public Double getVaIntake() {
		return vaIntake;
	}
	public void setVaIntake(Double vaIntake) {
		this.vaIntake = vaIntake;
	}
	public Integer getVaRecommend() {
		return vaRecommend;
	}
	public void setVaRecommend(Integer vaRecommend) {
		this.vaRecommend = vaRecommend;
	}
	public Double getVaPer() {
		vaPer = FunctionUtils.setDecimal(vaIntake / vaRecommend * 100, 1);
		return vaPer;
	}
	public void setVaPer(Double vaPer) {
		this.vaPer = vaPer;
	}
	public Double getVbIntake() {
		return vbIntake;
	}
	public void setVbIntake(Double vbIntake) {
		this.vbIntake = vbIntake;
	}
	public Double getVbRecommend() {
		return vbRecommend;
	}
	public void setVbRecommend(Double vbRecommend) {
		this.vbRecommend = vbRecommend;
	}
	public Double getVbPer() {
		vbPer = FunctionUtils.setDecimal(vbIntake / vbRecommend * 100, 1);
		return vbPer;
	}
	public void setVbPer(Double vbPer) {
		this.vbPer = vbPer;
	}
	public Double getVcIntake() {
		return vcIntake;
	}
	public void setVcIntake(Double vcIntake) {
		this.vcIntake = vcIntake;
	}
	public Integer getVcRecommend() {
		return vcRecommend;
	}
	public void setVcRecommend(Integer vcRecommend) {
		this.vcRecommend = vcRecommend;
	}
	public Double getVcPer() {
		vcPer = FunctionUtils.setDecimal(vcIntake / vcRecommend * 100, 1);
		return vcPer;
	}
	public void setVcPer(Double vcPer) {
		this.vcPer = vcPer;
	}
	public Double getHighQualityProIntake() {
		return highQualityProIntake;
	}
	public void setHighQualityProIntake(Double highQualityProIntake) {
		this.highQualityProIntake = highQualityProIntake;
	}
	public Double getNonPrimeProIntake() {
		return nonPrimeProIntake;
	}
	public void setNonPrimeProIntake(Double nonPrimeProIntake) {
		this.nonPrimeProIntake = nonPrimeProIntake;
	}
	
	//营养素状态
	public Integer getEnergyStatus() {
		double min = energyRecommend - FunctionUtils.setDecimal(energyRecommend * 0.05, 1);
		double max = energyRecommend + FunctionUtils.setDecimal(energyRecommend * 0.05, 1);
		if(energyIntake < min){
			energyStatus = 0;
		}else if(energyIntake > max){
			energyStatus = 2;
		}else{
			energyStatus = 1;
		}
		return energyStatus;
	}
	public void setEnergyStatus(Integer energyStatus) {
		this.energyStatus = energyStatus;
	}
	public Integer getProteinStatus() {
		if(proteinIntake<proteinRecommend[0]){
			proteinStatus = 0;
		}else if(proteinIntake>proteinRecommend[1]){
			proteinStatus = 2;
		}else{
			proteinStatus = 1;
		}
		return proteinStatus;
	}
	public void setProteinStatus(Integer proteinStatus) {
		this.proteinStatus = proteinStatus;
	}
	public Integer getFatStatus() {
		if(fatIntake<fatRecommend[0]){
			fatStatus = 0; 
		}else if(fatIntake>fatRecommend[1]){
			fatStatus = 2;
		}else{
			fatStatus = 1;
		}
		return fatStatus;
	}
	public void setFatStatus(Integer fatStatus) {
		this.fatStatus = fatStatus;
	}
	public Integer getCarbonStatus() {
		if(carbonIntake<carbonRecommend[0]){
			carbonStatus = 0;
		}else if(carbonIntake>carbonRecommend[1]){
			carbonStatus = 2;
		}else{
			carbonStatus = 1;
		}
		return carbonStatus;
	}
	public void setCarbonStatus(Integer carbonStatus) {
		this.carbonStatus = carbonStatus;
	}
	public Integer getCaStatus() {
		if(caIntake<caRecommend){
			caStatus = 0;
		}else if(caIntake>2000){
			caStatus = 2;
		}else{
			caStatus = 1;
		}
		return caStatus;
	}
	public void setCaStatus(Integer caStatus) {
		this.caStatus = caStatus;
	}
	public Integer getFeStatus() {
		if(feIntake<feRecommend){
			feStatus = 0;
		}else if(feIntake>42){
			feStatus = 2;
		}else{
			feStatus = 1;
		}
		return feStatus;
	}
	public void setFeStatus(Integer feStatus) {
		this.feStatus = feStatus;
	}
	public Integer getZnStatus() {
		if(znIntake<znRecommend){
			znStatus = 0;
		}else if(znIntake>40){
			znStatus = 2;
		}else{
			znStatus = 1;
		}
		return znStatus;
	}
	public void setZnStatus(Integer znStatus) {
		this.znStatus = znStatus;
	}
	public Integer getSeStatus() {
		if(seIntake<seRecommend){
			seStatus = 0;
		}else if(seIntake>400){
			seStatus = 2;
		}else{
			seStatus = 1;
		}
		return seStatus;
	}
	public void setSeStatus(Integer seStatus) {
		this.seStatus = seStatus;
	}
	public Integer getCuStatus() {
		if(cuIntake<cuRecommend){
			cuStatus = 0;
		}else if(cuIntake>8){
			cuStatus = 2;
		}else{
			cuStatus = 1;
		}
		return cuStatus;
	}
	public void setCuStatus(Integer cuStatus) {
		this.cuStatus = cuStatus;
	}
	public Integer getNaStatus() {
		if(naIntake<naRecommend){
			naStatus = 0;
		}else{
			naStatus = 1;
		}
		return naStatus;
	}
	public void setNaStatus(Integer naStatus) {
		this.naStatus = naStatus;
	}
	public Integer getiStatus() {
		if(iIntake<iRecommend){
			iStatus = 0;
		}else if(iIntake>600){
			iStatus = 2;
		}else{
			iStatus = 1;
		}
		return iStatus;
	}
	public void setiStatus(Integer iStatus) {
		this.iStatus = iStatus;
	}
	public Integer getpStatus() {
		if(pIntake<pRecommend){
			pStatus = 0;
		}else if(pIntake>3500){
			pStatus = 2;
		}else{
			pStatus = 1;
		}
		return pStatus;
	}
	public void setpStatus(Integer pStatus) {
		this.pStatus = pStatus;
	}
	public Integer getkStatus() {
		if(kIntake<kRecommend){
			kStatus = 0;
		}else{
			kStatus = 1;
		}
		return kStatus;
	}
	public void setkStatus(Integer kStatus) {
		this.kStatus = kStatus;
	}
	public Integer getMgStatus() {
		if(mgIntake<mgRecommend){
			mgStatus = 0;
		}else{
			mgStatus = 1;
		}
		return mgStatus;
	}
	public void setMgStatus(Integer mgStatus) {
		this.mgStatus = mgStatus;
	}
	public Integer getMnStatus() {
		if(mnIntake<mnRecommend){
			mnStatus = 0;
		}else if(mnIntake>11){
			mnStatus = 2;
		}else{
			mnStatus = 1;
		}
		return mnStatus;
	}
	public void setMnStatus(Integer mnStatus) {
		this.mnStatus = mnStatus;
	}
	public Integer getVaStatus() {
		if(vaIntake<vaRecommend){
			vaStatus = 0;
		}else if(vaIntake>3000){
			vaStatus = 2;
		}else{
			vaStatus = 1;
		}
		return vaStatus;
	}
	public void setVaStatus(Integer vaStatus) {
		this.vaStatus = vaStatus;
	}
	public Integer getVbStatus() {
		if(vbIntake<vbRecommend){
			vbStatus = 0;
		}else if(vbIntake>60){
			vbStatus = 2;
		}else{
			vbStatus = 1;
		}
		return vbStatus;
	}
	public void setVbStatus(Integer vbStatus) {
		this.vbStatus = vbStatus;
	}
	public Integer getVcStatus() {
		if(vcIntake<vcRecommend){
			vcStatus = 0;
		}else if(vcIntake>2000){
			vcStatus = 2;
		}else{
			vcStatus = 1;
		}
		return vcStatus;
	}
	public void setVcStatus(Integer vcStatus) {
		this.vcStatus = vcStatus;
	}
	
}
