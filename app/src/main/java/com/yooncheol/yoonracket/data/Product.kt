package com.yooncheol.yoonracket.data

import java.io.Serializable

class Product : Serializable{
    var id : String? = null
    var name : String? = null
    var company : String? = null
    var weight1 : Int? = null
    var weight2 : Int? = null
    var balance : String? = null
    var shaft1 : String? = null
    var shaft2 : String? = null
    var price : Int? = null
    var imagePath : String? = null
    var description : String? = null
    var commentList: List<String>? = null

    constructor(){

    }
}