package com.leichuang.tradecoin.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("获取货币价格接口入参")
public class GetCoinPriceParam implements Serializable {
    private static final long serialVersionUID = 5518118594811791674L;

    @NotNull
    @ApiModelProperty(value = "需要获取的货币名", required = true, example = "[btc,eth]")
    private List<String> coins;

}
