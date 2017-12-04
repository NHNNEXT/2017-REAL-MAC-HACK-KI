//
//  Post.swift
//  Amigo
//
//  Created by Yongjae Kwon on 2017. 11. 30..
//  Copyright © 2017년 Yongjae Kwon. All rights reserved.
//

import Foundation

struct Login: Encodable, Decodable {
    let email: String
    let password: String
}

struct SignUp: Encodable, Decodable {
    let email: String
    let name: String
    let password: String
}
