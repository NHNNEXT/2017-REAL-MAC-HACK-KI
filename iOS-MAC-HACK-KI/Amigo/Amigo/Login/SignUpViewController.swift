//
//  SignUpViewController.swift
//  Amigo
//
//  Created by Yongjae Kwon on 2017. 11. 30..
//  Copyright © 2017년 Yongjae Kwon. All rights reserved.
//

import UIKit

class SignUpViewController: UIViewController {
    @IBOutlet weak var nameTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var emailTextField: UITextField!
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func dismissBtnClicked(_ sender: Any) {
        dismiss(animated: true) {
            print("Dismissing Loader view Controller")
        }
    }
    
    @IBAction func signUpBtnClicked(_ sender: Any) {
        guard let url = URL(string: "http://dev.amigotrip.co.kr/users") else {
            return
        }
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        
        let post = SignUp(email: emailTextField.text!, name: nameTextField.text!, password: passwordTextField.text!)
        do {
            let jsonBody = try JSONEncoder().encode(post)
            request.httpBody = jsonBody
        } catch {}
        
        let session = URLSession.shared
        let task = session.dataTask(with: request) { (data, _, _) in
            guard let data = data else {
                return
            }
            do {
                let sentPost = try JSONDecoder().decode(SignUp.self, from: data)
                print("!!!!!!!!!!! \(sentPost)")
            } catch {}
        }
        task.resume()
    }
}
