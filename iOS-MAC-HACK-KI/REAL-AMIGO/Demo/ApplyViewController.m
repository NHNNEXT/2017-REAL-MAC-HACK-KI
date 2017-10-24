//
//  ApplyViewController.m
//  REAL-AMIGO
//
//  Created by YongJai on 19/10/2017.
//  Copyright © 2017 YongJai. All rights reserved.
//

#import "ApplyViewController.h"

@interface ApplyViewController ()

@end

@implementation ApplyViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

- (IBAction)clickedSubmitBtn:(id)sender {
    NSURLSessionConfiguration *defaultConfigObject = [NSURLSessionConfiguration defaultSessionConfiguration];
    NSURLSession *defaultSession = [NSURLSession sessionWithConfiguration:defaultConfigObject];
    
    NSURL *url = [NSURL URLWithString:@"http://211.249.60.54/party/guest"];
    NSMutableURLRequest *submitRequest = [NSMutableURLRequest requestWithURL:url];
    
    [submitRequest setValue:@"application/json" forHTTPHeaderField:@"content-Type"];
    [submitRequest setHTTPMethod:@"POST"];
    
    NSDictionary *submitData = @{
                                 @"name" : _nameTextField.text,
                                 @"email" : _emailTextField.text,
                                 @"age": _ageTextField.text,
                                 @"gender": _genderTextField.text,
                                 @"language": _languageTextField.text,
                                 @"date": _dateTextField.text,
                                 @"theme": _themeTextField.text,
                                 @"attraction": _attractionTextField.text
                                 };
    
    [submitRequest setHTTPBody:[NSJSONSerialization dataWithJSONObject:submitData options:0 error:nil]];
    
    NSURLSessionDataTask *dataTask = [defaultSession dataTaskWithRequest:submitRequest completionHandler:^(NSData *data, NSURLResponse *response, NSError *error) {
        if (error == nil) {
            NSString *request = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
            NSLog(@"Data = %@", request);
        }
    }];
    [dataTask resume];
}

@end
