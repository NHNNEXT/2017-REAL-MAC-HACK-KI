//
//  ViewController.m
//  REAL-AMIGO
//
//  Created by YongJai on 14/10/2017.
//  Copyright © 2017 YongJai. All rights reserved.
//

#import "ViewController.h"
#import "ApplyViewController.h"
//
//#import <FBSDKLoginKit/FBSDKLoginKit.h>
//#import <FBSDKCoreKit/FBSDKCoreKit.h>

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if ([[segue identifier] isEqualToString:@"segue"]) {
        ApplyViewController *vc = segue.destinationViewController;
        vc.data = _emailTextField.text;
    }
}

- (void)openScheme:(NSString *)scheme {
    UIApplication *application = [UIApplication sharedApplication];
    NSURL *URL = [NSURL URLWithString:scheme];
    [application openURL:URL options:@{} completionHandler:^(BOOL success) {
        if (success) {
            NSLog(@"Opened %@",scheme);
        }
    }];
}

- (IBAction)clickedGetInfo:(id)sender {
    [self openScheme:@"http://amigotrip.co.kr/"];
}

-(void)textFieldDidBeginEditing:(UITextField *)textField {
    [self animateTextField:textField up:YES];
}

- (void)textFieldDidEndEditing:(UITextField *)textField {
    [self animateTextField:textField up:NO];
}

-(void)animateTextField:(UITextField*)textField up:(BOOL)up {
    const int movementDistance = -200;
    const float movementDuration = 0.3f;
    
    int movement = (up ? movementDistance : -movementDistance);
    
    [UIView beginAnimations: @"animateTextField" context: nil];
    [UIView setAnimationBeginsFromCurrentState: YES];
    [UIView setAnimationDuration: movementDuration];
    self.view.frame = CGRectOffset(self.view.frame, 0, movement);
    [UIView commitAnimations];
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    [textField resignFirstResponder];
    return YES;
}

- (BOOL)textView:(UIView *)textView shouldChangeTextInRange:(NSRange)range replacementText:(nonnull NSString *)text {
    if ([text isEqualToString:@"\n"]) {
        [textView resignFirstResponder];
        return NO;
    }
    return YES;
}

//
//- (IBAction)facebookLoginBtn:(id)sender {
//    if ([[NSUserDefaults standardUserDefaults] objectForKey:@"login_details"]) {
//        NSDictionary *loginDict = [[NSUserDefaults standardUserDefaults] objectForKey:@"login_details"];
//        UIAlertController * alert = [UIAlertController
//                                     alertControllerWithTitle:[loginDict valueForKey:@"last_name"]
//                                     message:nil
//                                     preferredStyle:UIAlertControllerStyleAlert];
//        [self presentViewController:alert animated:YES completion:nil];
//    }
//
//    FBSDKLoginManager *loginManager = [[FBSDKLoginManager alloc] init];
//
//    [loginManager logInWithReadPermissions:@[@"email", @"public_profile"] fromViewController:self handler:^(FBSDKLoginManagerLoginResult *result, NSError *error){
//        if (error) {
//            NSLog(@"Process Error");
//        } else if (result.isCancelled) {
//            NSLog(@"Result Cancelled");
//        } else {
//            NSLog(@"Logged In");
//            NSLog(@"Result - %@", result.token.userID);
//            [self fetchUserInfo:result.token.userID];
//        }
//    }];
//}
//
//- (void)fetchUserInfo:(NSString *)userID {
//    if ([FBSDKAccessToken currentAccessToken]) {
//        [[[FBSDKGraphRequest alloc] initWithGraphPath:@"me" parameters:@{@"fields":@"id, name, first_name, last_name, picture, email"}] startWithCompletionHandler:^(FBSDKGraphRequestConnection *connection, id result, NSError *error){
//            if (!error) {
//                NSLog(@"Response - %@", result);
//                [[NSUserDefaults standardUserDefaults] setObject:result forKey:@"login_details"];
//                [[NSUserDefaults standardUserDefaults] synchronize];
//                UIAlertController * alert = [UIAlertController
//                                             alertControllerWithTitle:[result valueForKey:@"last_name"]
//                                             message:nil
//                                             preferredStyle:UIAlertControllerStyleAlert];
//                [self presentViewController:alert animated:YES completion:nil];
//            } else {
//                NSLog(@"Error - %@", error.description);
//            }
//        }];
//    }
//}







@end
