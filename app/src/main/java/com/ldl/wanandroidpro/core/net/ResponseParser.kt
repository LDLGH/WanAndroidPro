package com.ldl.wanandroidpro.core.net

import com.ldl.wanandroidpro.core.model.BaseResponse
import okhttp3.Response
import okio.IOException
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.entity.ParameterizedTypeImpl
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.AbstractParser
import java.lang.reflect.Type

/**
 * 作者：LDL 创建时间：2020/6/3
 * 类说明：
 */
@Parser(name = "Response")
open class ResponseParser<T> : AbstractParser<T> {

    //以下两个构造方法是必须的
    protected constructor() : super()

    constructor(type: Class<T>) : super(type)

    @Throws(IOException::class)
    override fun onParse(response: Response): T {
        val type: Type = ParameterizedTypeImpl[BaseResponse::class.java, mType] //获取泛型类型
        val data: BaseResponse<T> = convert(response, type)   //获取Response对象
        val t = data.data
        //获取data字段
        if (data.errorCode != BaseResponse.SUCCESS || t == null) { //code不等于0，说明数据不正确，抛出异常
            throw ParseException(data.errorCode.toString(), data.errorMsg, response)
        }
        return t
    }
}
