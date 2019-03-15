package com.leichuang.tradecoin.utils;

import com.leichuang.tradecoin.entity.OrderPO;
import com.leichuang.tradecoin.entity.TickerPO;
import com.leichuang.tradecoin.entity.UserPO;
import com.leichuang.tradecoin.vo.AccountInfoVo;
import com.leichuang.tradecoin.vo.OrderInfoVo;
import com.leichuang.tradecoin.vo.RealTimeData;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

public class ConvertUtil {

	public static TickerPO convertTicker(RealTimeData realTimeData){
		TickerPO ticker = new TickerPO();
		ticker.setBuy(realTimeData.getTicker().getBuy());
		ticker.setHigh(realTimeData.getTicker().getHigh());
		ticker.setLast(realTimeData.getTicker().getLast());
		ticker.setLow(realTimeData.getTicker().getLow());
		ticker.setOpen(realTimeData.getTicker().getOpen());
		ticker.setSell(realTimeData.getTicker().getSell());
		ticker.setSymbol(realTimeData.getTicker().getSymbol());
		ticker.setVol(realTimeData.getTicker().getVol());
		ticker.setTime(realTimeData.getTime());
		return ticker;
	}
	
	public static OrderPO convertOrderIno(OrderInfoVo orderInfoVo){
		OrderPO orderInfo = new OrderPO();
		try {
			BeanUtils.copyProperties(orderInfo, orderInfoVo);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return orderInfo;
	}
	

}

