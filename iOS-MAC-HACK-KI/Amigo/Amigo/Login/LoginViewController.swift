//
//  LoginViewController.swift
//  Amigo
//
//  Created by Yongjae Kwon on 2017. 11. 29..
//  Copyright © 2017년 Yongjae Kwon. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController {
//    let animator = Animation()
    @IBOutlet weak var loginHiddenView: UIView!
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var errorLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    @IBAction func loginBtnClicked(_ sender: Any) {
        guard let url = URL(string: "http://dev.amigotrip.co.kr/users/login") else {
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        
        let post = Login(email: emailTextField.text!, password: passwordTextField.text!)
        do {
            let jsonBody = try JSONEncoder().encode(post)
            request.httpBody = jsonBody
        } catch {}
        
        let session = URLSession.shared
        let task = session.dataTask(with: request) { (data, response, error) in
            do {
                let sentPost = try JSONDecoder().decode(Login.self, from: data!)
                print(sentPost)
                
                // main queue에서만 뷰 관련 작업을 할 수 있다.
                DispatchQueue.main.async(execute: {
                    let storyboard = UIStoryboard(name: "Main", bundle: nil)
                    let vc = storyboard.instantiateViewController(withIdentifier: "main") as! UITabBarController
                    self.present(vc, animated: true, completion: nil)
                })

            } catch {
                
                DispatchQueue.main.async(execute: {
                    self.errorLabel.isHidden = false
                    Timer.scheduledTimer(timeInterval: 1.5, target: self, selector: #selector(self.hideLabel), userInfo: nil, repeats: false)
                })
            }
        }
        task.resume()
    }
    
    @objc func hideLabel() {
        self.errorLabel.isHidden = true
    }
    @IBAction func loginPopUp(_ sender: Any) {
        loginHiddenView.isHidden = false
    }
    
    @IBAction func cancelBtnClicked(_ sender: Any) {
        loginHiddenView.isHidden = true
        emailTextField.text = ""
        passwordTextField.text = ""
    }
    
    @IBAction func tourBtnClicked(_ sender: Any) {
    }
    
    
    @IBAction func signUpBtnClicked(_ sender: Any) {
    }
    
}
