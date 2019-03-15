package com.leichuang.tradecoin.consts;


import com.leichuang.tradecoin.exception.CoinException;
import com.leichuang.tradecoin.exception.ErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CoinEnum {

    BNB("bnb", "bnb", "binance"),
    HT("ht", "ht ", "huobi"),
    BTC("btc", "btc", "huobi"),
    BSV("bsv", "bsv", "huobi"),
    BCH("bch", "bch", "huobi"),
    ETH("eth", "eth", "huobi"),
    LTC("ltc", "ltc", "huobi"),
    ETC("etc", "etc", "huobi"),
    EOS("eos", "eos", "huobi"),
    ;

    private String symbol;
    private String desc;
    private String market;


    /**
     * 根据缩写获取到币种
     **/
    public static CoinEnum getBySymbol(String symbol) {

        for (CoinEnum coinEnum : CoinEnum.values()) {
            if (coinEnum.symbol.equalsIgnoreCase(symbol)) {
                return coinEnum;
            }
        }
        throw new CoinException(ErrorCodes.INVALID_COIN_TYPE);

    }


}
