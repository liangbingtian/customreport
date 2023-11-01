package apitest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.liang.customreport.enums.JdApiEnum;
import com.liang.customreport.jdapicall.JdApiV2Service;
import com.liang.customreport.jdapicall.bo.ParamInfo;
import com.liang.customreport.jdapicall.bo.common.JdApiV2ResultBo;
import com.liang.customreport.jdapicall.bo.customreport.JingdongAdsIbgCustomQueryV1ReqBO;
import com.liang.customreport.jdapicall.bo.customreport.JingdongAdsIbgCustomQueryV1ResBO;
import java.io.InputStream;
import java.util.Optional;
import org.junit.Test;

/**
 * @author liangbingtian
 * @date 2023/11/01 下午12:25
 */
public class ApiCallTest {

  @Test
  public void callApiTest() {
    try (InputStream inputStream = this.getClass().getClassLoader()
        .getResourceAsStream("api/customreport_queryparam.json")) {
      final JingdongAdsIbgCustomQueryV1ReqBO o = JSON.parseObject(
          Optional.ofNullable(inputStream).orElseThrow(() -> new RuntimeException("读取的为null")),
          JingdongAdsIbgCustomQueryV1ReqBO.class, Feature.OrderedField);
      final ParamInfo info = ParamInfo.builder()
          .accessToken("1fb2353257524bf88d3a067195bdba82mzi1")
          .appKey("A1D3C721A3E382FF4915BE266B4294F6")
          .appSecret("8d08db8de0ec468ebe234dcfdc1c3dca")
          .api(JdApiEnum.CUSTOM_REPORT_QUERY.getApi())
          .build();
      JdApiV2Service<JingdongAdsIbgCustomQueryV1ReqBO, JingdongAdsIbgCustomQueryV1ResBO> service = new JdApiV2Service<>();
      final JdApiV2ResultBo<JingdongAdsIbgCustomQueryV1ResBO> result = service
          .doRequest(o, info);
      System.out.println();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
