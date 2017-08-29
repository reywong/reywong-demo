package cn.com.yto56.coresystem.stl.soa.logic.module.dao;

import java.util.List;

import cn.com.yto56.coresystem.stl.soa.logic.module.domain.TStlBNetDetail;

public interface StlSoaForEinvoiceDao {
	
	List<TStlBNetDetail> selectBnetAmount(String[] waybillNo ); 

}
