//
//  ViewController.m
//  REAL-AMIGO
//
//  Created by YongJai on 14/10/2017.
//  Copyright © 2017 YongJai. All rights reserved.
//

#import "ViewController.h"
#import "ApplyViewController.h"

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


@end
