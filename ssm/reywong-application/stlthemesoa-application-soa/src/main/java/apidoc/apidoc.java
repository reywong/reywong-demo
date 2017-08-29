/**
 * @apiDefine apiResponse
 * @apiParam (返回头) {Boolean} result_flag     是否操作成功.
 * @apiParam (返回头) {String} result_code     接口返回状态码.<a href="#api-paraminfo-resultCode">状态码</a>
 * @apiParam (返回头) {String} result_message  接口返回信息.
 */

/**
 * @apiDefine apiRequest
 * @apiParam (请求头)   {String} stl_soa_appid  财务系统分配的stl_soa_appid
 */

/**
 * @api {post} /register stlthemesoa测试接口-登陆
 * @apiDescription 接口参数全小写,以slt_module_param格式
 *
 * @apiName register
 * @apiGroup soa
 * @apiVersion 1.0.0
 *
 * @apiUse apiRequest
 *
 * @apiParam (请求参数) {String} stl_soa_sysname 系统名称
 * @apiParam (请求参数) {String} [stl_soa_sysremark] 系统备注
 * @apiParam (请求参数) {String} stl_soa_key 用户自行设置，用于签名.<a target='_blank' href='https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=4_3'> 签名算法</a>
 *
 * @apiUse apiResponse
 *
 * @apiParamExample 请求实例：
 *  {
 *      "stl_soa_sysname":"会员系统",
 *      "stl_soa_sysremark":"需要发票接口簇",
 *      "stl_soa_key":"reywong"
 *
 *  }
 *
 * @apiSuccessExample 成功返回:
 *     {
 *       "result_flag":true,
 *       "result_code":"success",
 *       "result_message": "返回成功",
 *       "result_data":{
 *           "stl_soa_appid":"50cb6595-b103-4bb4-8beb-ccbf78f6fe67"，
 *       }
 *     }
 *
 *
 * @apiErrorExample 失败返回:
 *     {
 *       "result_flag":false,
 *       "result_code":"false",
 *       "result_message": "stl_soa_key不能为空",
 *       "result_data":null
 *      }
 */


/**
 * @api {post} /resultcode 状态码
 * @apiDescription 接口返回码介绍
 * @apiName resultCode
 * @apiGroup paraminfo
 *
 * @apiParam (result_code列表) {String} success  返回成功
 * @apiParam (result_code列表) {String} fail  返回失败
 * @apiParam (result_code列表) {String} error  服务端异常
 *
 */

