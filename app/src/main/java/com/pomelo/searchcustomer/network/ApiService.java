package com.pomelo.searchcustomer.network;

import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.bean.AboutUsBean;
import com.pomelo.searchcustomer.bean.CardSquareBean;
import com.pomelo.searchcustomer.bean.CustomerBean;
import com.pomelo.searchcustomer.bean.DisclaimerBean;
import com.pomelo.searchcustomer.bean.HelpCenterBean;
import com.pomelo.searchcustomer.bean.HelpDetailBean;
import com.pomelo.searchcustomer.bean.HomeIndexBean;
import com.pomelo.searchcustomer.bean.LatLonBean;
import com.pomelo.searchcustomer.bean.MessageModelBean;
import com.pomelo.searchcustomer.bean.MyCollectionBean;
import com.pomelo.searchcustomer.bean.MyCollectionInfoBean;
import com.pomelo.searchcustomer.bean.MyMessageBean;
import com.pomelo.searchcustomer.bean.MySupplyBean;
import com.pomelo.searchcustomer.bean.MySupplyDetailBean;
import com.pomelo.searchcustomer.bean.MyVisitinCardBean;
import com.pomelo.searchcustomer.bean.ShopDetailBean;
import com.pomelo.searchcustomer.bean.ShopListBean;
import com.pomelo.searchcustomer.bean.SullierBean;
import com.pomelo.searchcustomer.bean.SupplierClassBean;
import com.pomelo.searchcustomer.bean.UserInfoBean;
import com.pomelo.searchcustomer.bean.VistingCardBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by wanghaoxiang on 2019-07-20.
 * 所有接口的封装
 */

public interface ApiService {
    /**
     * 注册
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Register/Appindex")
    Observable<BaseModel> register(@Field("token") String accessToken, @Field("version") String version, @Field("password") String password, @Field("mobile") String mobile, @Field("truepassword") String truepassword, @Field("code") String code, @Field("brand") String brand, @Field("model") String model, @Field("devices_id") String devices_id);

    /**
     * 登录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Login/login")
    Observable<BaseModel> login(@Field("token") String accessToken, @Field("version") String version, @Field("password") String password, @Field("mobile") String mobile, @Field("brand") String brand, @Field("model") String model, @Field("devices_id") String devices_id);

    /**
     * 修改密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Register/Appalter")
    Observable<BaseModel> modifypwd(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken, @Field("password") String password, @Field("truepassword") String truepassword, @Field("code") String code, @Field("mobile") String mobile, @Field("brand") String brand, @Field("model") String model, @Field("devices_id") String devices_id);

    /**
     * 获取首页数据
     *
     * @return
     */
    @FormUrlEncoded
    @POST("index/indexall")
    Observable<BaseModel<HomeIndexBean>> getIndex(@Field("token") String accessToken, @Field("version") String version);

    /**
     * 获取用户信息
     *
     * @param accessToken
     * @param version
     * @return
     */
    @FormUrlEncoded
    @POST("Login/getUserinfo")
    Observable<BaseModel<UserInfoBean>> getUserInfo(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken);

    /**
     * 搜索
     *
     * @return
     */
    @FormUrlEncoded
    @POST("index/indexall")
    Observable<BaseModel<HomeIndexBean>> searchCustomer(@Field("token") String accessToken, @Field("version") String version, @Field("type") String type);

    /**
     * 获取供需库分类
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Need/needType")
    Observable<BaseModel<SupplierClassBean>> getSpplierClass(@Field("token") String accessToken, @Field("version") String version);

    /**
     * 我的供应列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Need/userNeed")
    Observable<BaseModel<MySupplyBean>> getMySupplyList(@Field("token") String accessToken, @Field("version") String version, @Field("type") String type, @Field("utoken") String utoken);

    /**
     * 删除我的供应
     *
     * @param accessToken
     * @param version
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("Need/del")
    Observable<BaseModel<MySupplyBean>> deleteMySupply(@Field("token") String accessToken, @Field("version") String version, @Field("id") String id, @Field("utoken") String utoken);

    /**
     * 获取供需库列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Need/needList")
    Observable<BaseModel<SullierBean>> getSupplierList(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken, @Field("type") String type, @Field("page") String page, @Field("pagenum") String pagenum, @Field("needType") String needType, @Field("second_nav_action") String secondNavAction);

    /**
     * 投诉
     *
     * @param accessToken
     * @param version
     * @param utoken
     * @return
     */
    @FormUrlEncoded
    @POST("Need/complaint")
    Observable<BaseModel> complaint(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken, @Field("n_id") String niId, @Field("content") String content);

    /**
     * 供需库添加
     *
     * @return
     */
    @Multipart
    @FormUrlEncoded
    @POST("Need/addNeed")
    Observable<BaseModel> addSupplier(@Field("token") String accessToken, @Field("version") String version, @Field("title") String title, @Field("content") String content, @Field("mobile") String mobile, @Field("type") String type, @Field("need_type") String need_type, @PartMap Map<String, RequestBody> parts);

    /**
     * 获取验证码
     *
     * @param accessToken
     * @param mobile
     * @return
     */
    @FormUrlEncoded
    @POST("Register/Appcode")
    Observable<BaseModel> getMessageCode(@Field("token") String accessToken, @Field("version") String version, @Field("mobile") String mobile);

    /**
     * 获取修改密码中的获取验证码
     *
     * @param accessToken
     * @param version
     * @param mobile
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("Register/Appcode")
    Observable<BaseModel> getModifyPwdMessageCode(@Field("token") String accessToken, @Field("version") String version, @Field("mobile") String mobile);

    /**
     * 获取供需详情
     *
     * @param accessToken
     * @return
     */
    @FormUrlEncoded
    @POST("Need/needDetail")
    Observable<BaseModel<MySupplyDetailBean>> getSupplierDetail(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken, @Field("id") String id, @Field("mytype") String mytype);

    /**
     * 联系我们
     *
     * @param accessToken
     * @param version
     * @param utoken
     * @return
     */
    @FormUrlEncoded
    @POST("Contact/contactList")
    Observable<BaseModel<List<AboutUsBean>>> linkUs(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken);

    /**
     * 店铺详情
     *
     * @param accessToken
     * @param version
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("index/shop")
    Observable<BaseModel<ShopDetailBean>> getShopDetail(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken, @Field("id") String id);

    /**
     * @param accessToken
     * @param version
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("map/apptransition")
    Observable<BaseModel<List<CustomerBean>>> collectionData(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken, @Field("query") String quary, @Field("region") String region, @Field("id") String id, @Field("page") String page, @Field("pagenum") String pagenum, @Field("length") String length);

    /**
     * 获取经纬度
     *
     * @param accessToken
     * @param version
     */
    @FormUrlEncoded
    @POST("map/coordinate")
    Observable<BaseModel<LatLonBean>> getLanLat(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken, @Field("region") String region);

    /**
     * 获取我的名片
     *
     * @param accessToken
     * @return
     */
    @FormUrlEncoded
    @POST("Card/cardMyInfo")
    Observable<BaseModel<MyVisitinCardBean>> getMyVisitingCard(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken);

    /**
     * 获取名片详情
     *
     * @param accessToken
     * @return
     */
    @FormUrlEncoded
    @POST("Card/cardMyInfo")
    Observable<BaseModel<MyVisitinCardBean>> getOtherPersonVisitingCard(@Field("token") String accessToken, @Field("version") String version, @Field("card_id") String id);

    /**
     * 获取红店店铺列表
     *
     * @param accessToken
     * @param version
     * @return
     */
    @FormUrlEncoded
    @POST("Index/AppshopList")
    Observable<BaseModel<ShopListBean>> getShopList(@Field("token") String accessToken, @Field("version") String version, @Field("page") String page, @Field("pagenum") String pagenum, @Field("searchVale") String searchVale);

    /**
     * 获取数据源
     *
     * @param accessToken
     * @param version
     * @return
     */
    @FormUrlEncoded
    @POST("Yunsource/sourceList")
    Observable<BaseModel<List<HomeIndexBean.Source>>> getSourceList(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken);

    /**
     * 收藏名片
     *
     * @param accessToken
     * @param version
     * @param utoken
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("Card/conllec")
    Observable<BaseModel> collectionCard(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken, @Field("card_id") String id);

    /**
     * 取消收藏名片
     *
     * @param accessToken
     * @param version
     * @param utoken
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("Card/cancelConllec")
    Observable<BaseModel> cancleCollectionCard(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken, @Field("card_id") String id);

    /**
     * 靠谱和取消靠谱
     *
     * @param accessToken
     * @param version
     * @param utoken
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("Card/careReliable")
    Observable<BaseModel> careReliableCard(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken, @Field("card_id") String id);

    /**
     * 获取名片广场
     *
     * @param accessToken
     * @return
     */
    @FormUrlEncoded
    @POST("Card/cardSquare")
    Observable<BaseModel<CardSquareBean>> getCardSquare(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken, @Field("page") String page, @Field("code") String code, @Field("keyword") String keyword, @Field("card_hy_id") String cardHyId);

    /**
     * 我的采集中获取采集途径以及分类
     *
     * @param accessToken
     * @param version
     * @param utoken
     * @return
     */

    @FormUrlEncoded
    @POST("Export/myExport")
    Observable<BaseModel<MyCollectionInfoBean>> getMyCollectionInfo(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken);

    /**
     * 我的小虾
     *
     * @param accessToken
     * @param version
     * @param utoken
     * @return
     */
    @FormUrlEncoded
    @POST("Notice/NoticeList")
    Observable<BaseModel<MyMessageBean>> getMyMessage(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken, @Field("page") String page, @Field("pagenum") String pagenum);

    /**
     * 获取我的采集中的数据
     *
     * @param accessToken
     * @param version
     * @param utoken
     * @return
     */
    @FormUrlEncoded
    @POST("Export/data")
    Observable<BaseModel<MyCollectionBean>> getMyCollectionData(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken, @Field("type") String type, @Field("content") String content, @Field("page") String page, @Field("pagenum") String pagenum);

    /**
     * 删除我的采集中的数据
     *
     * @param accessToken
     * @param version
     * @param utoken
     * @param type
     * @param content
     * @return
     */
    @FormUrlEncoded
    @POST("Dataque/clearData")
    Observable<BaseModel> deleteCollectionData(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken, @Field("type") String type, @Field("content") String content);

    /**
     * 一键导出数据
     *
     * @param accessToken
     * @param version
     * @param utoken
     * @param type
     * @param content
     * @return
     */
    @FormUrlEncoded
    @POST("Export/MapExport")
    Observable<BaseModel<String>> exportCollectionData(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken, @Field("type") String type, @Field("content") String content);

    /**
     * 超级搜中的分类
     *
     * @param accessToken
     * @param version
     * @param utoken
     * @return
     */
    @FormUrlEncoded
    @POST("search/searchType")
    Observable<BaseModel<List<String>>> getSearchType(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken);

    /**
     * 获取名片夹列表
     */
    @FormUrlEncoded
    @POST("Card/cardConllec")
    Observable<BaseModel<VistingCardBean>> getVisitinCardList(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken, @Field("keyword") String keyword, @Field("page") String page, @Field("pagenum") String pagenum);

    /**
     * 编辑名片
     */
    @FormUrlEncoded
    @POST("Card/cardMyEdit")
    Observable<BaseModel> editMyCard(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken,
                                     @Field("user_name") String username, @Field("mobile") String mobile, @Field("company_name") String companyname,
                                     @Field("industry_category") String industrycategory, @Field("position") String position, @Field("company_address") String companyAddress,
                                     @Field("email") String email, @Field("department") String department, @Field("tel_work") String telwork,
                                     @Field("wechat") String wechat, @Field("company_url") String companyurl, @Field("card_status") String cardstatus,
                                     @Field("card_hy_id") String cardhyid, @Field("card_place_code") String cardplacecode, @Field("card_place") String cardplace);

    /**
     * 帮助中心
     *
     * @param accessToken
     * @param version
     * @param utoken
     * @return
     */
    @FormUrlEncoded
    @POST("Help/helpList")
    Observable<BaseModel<List<HelpCenterBean>>> getHelpCenterList(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken);

    /**
     * 帮助中心内容
     *
     * @param accessToken
     * @param version
     * @param id          3是采集帮助，为6是供需中心，为5是名片广场
     * @return
     */
    @FormUrlEncoded
    @POST("Help/helpDetail")
    Observable<BaseModel<HelpDetailBean>> getHelpDetail(@Field("token") String accessToken, @Field("version") String version, @Field("utoken") String utoken, @Field("id") String id);

    /**
     * 获取免责声明
     */
    @GET("Disclaimer/disclaimerList")
    Observable<BaseModel<DisclaimerBean>> getDisclaimerAgreemnt(@Query("token") String accessToken, @Query("version") String version);

    /**
     * 意见反馈
     *
     * @param token
     * @param name
     * @param content
     * @param mobile
     * @return
     */
    @GET("Agree/addAgree")
    Observable<BaseModel> feedBackSubmit(@Query("token") String token, @Query("version") String version, @Query("name") String name, @Query("content") String content, @Query("mobile") String mobile);

    /**
     * 消息模板列表
     */
    @GET("Msmtel/msmList")
    Observable<BaseModel<MessageModelBean>> getMessageList(@Query("token") String accessToken, @Query("version") String version);

    /**
     * 添加消息模板
     */
    @GET("Msmtel/addMsm")
    Observable<BaseModel> addMessageModel(@Query("token") String accessToken, @Query("version") String version, @Query("content") String content);
}
