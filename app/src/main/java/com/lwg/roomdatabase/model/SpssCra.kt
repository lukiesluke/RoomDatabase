package com.lwg.roomdatabase.model

data class SpssCra(
    var iLoginI: String? = "",
    var iCkSum: String? = "",
    var iSpssRec: SpssRec? = null,
    override var serverTS: String? = "",
    override var reply: Reply? = null,
    override var iApi: String? = "",
    override var iGwtGud: String? = ""
) : BaseCraEx()