package com.coins.tradecoin.consts;

public class UriConsts {

    public static final String HUOBI_PRO_PREFIX ="https://api.huobipro.com";

    public static final String HUOBI_FUTURE_TICKER = "/market/detail/merged";
    public static final String HUOBI_FUTURE_DEPTH = "/market/depth";
    public static final String HUOBI_FUTURE_KLINE = "/market/history/kline";
    public static final String HUOBI_FUTURE_TRADE = "/market/history/trade";
    public static final String HOUBI_FUTURE_TICKER="/market/tickers";

    // 获取合约信息 /v1/contract_contract_info
    public static final String HUOBI_FUTURE_CONTRACT_INFO = "/api/v1/contract_contract_info";
    // 获取合约指数v1/contract_index
    public static final String HUOBI_FUTURE_CONTRACT_INDEX = "/api/v1/contract_index";
    // 获取合约最低最高限价/v1/contract_price_limit
    public static final String HUOBI_FUTURE_CONTRACT_PRICE_LIMIT = "/api/v1/contract_price_limit";

    // 获取合约总持仓量
    public static final String HUOBI_FUTURE_CONTRACT_OPEN_INTEREST = "/api/v1/contract_open_interest";

    // 订单明细
    public static final String HUOBI_FUTURE_CONTRACT_ORDER_DETAIL = "/api/v1/contract_order_detail";

    public static final String HUOBI_FUTURE_CONTRACT_HISORDERS = "/api/v1/contract_hisorders";

    // 批量下单contract_batchorder
    public static final String HUOBI_FUTURE_CONTRACT_BATCHORDER = "/api/v1/contract_batchorder";
    // account相关
    public static final String HUOBI_FUTURE_ALL_ACCOUNT="/v1/account/accounts";

    /** 时间戳 **/
    public static final String HUOBI_TIMESTAMP="/v1/common/timestamp";

    // binance api
    public static final String BINANCE_PREFIX="https://api.binance.com";


    public static final String BINANCE_MARKET_KLINE="/api/v1/klines";

}
