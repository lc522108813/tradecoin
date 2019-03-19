package com.coins.tradecoin.utils;

import com.coins.tradecoin.entity.OrderPO;
import com.coins.tradecoin.entity.TickerPO;
import com.coins.tradecoin.vo.OrderInfoVo;
import com.coins.tradecoin.vo.RealTimeData;
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

