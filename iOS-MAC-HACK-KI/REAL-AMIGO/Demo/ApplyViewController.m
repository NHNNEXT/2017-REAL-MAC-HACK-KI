//
//  ApplyViewController.m
//  REAL-AMIGO
//
//  Created by YongJai on 19/10/2017.
//  Copyright © 2017 YongJai. All rights reserved.
//

#import "ApplyViewController.h"
#import "CalendarViewController.h"

@interface ApplyViewController () 

@end

@implementation ApplyViewController {
    Checkbox *cbox;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    _emailLabel.text = _data;
    NSNotificationCenter *noti = [NSNotificationCenter defaultCenter];
    [noti addObserver:self selector:@selector(blurViewhidden) name:@"dismissNoti" object:nil];
    [noti addObserver:self selector:@selector(getDate:) name:@"dateNoti" object:nil];
}
    
    //    calendarViewController = [[[NSBundle mainBundle] loadNibNamed:@"CalendarViewController" owner:self options:nil] objectAtIndex:0];
    
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

- (void)blurViewhidden {
    _blurView.hidden = YES;
}


- (void)getDate:(NSNotification *)notification{
//    _dateLabel.text = [notification object];
    _date = [notification object];
    _dateLabel.text = [NSString stringWithFormat:@"You come to Korea on %@", notification.object];
    _blurView.hidden = YES;

}

- (IBAction)clickedSubmitBtn:(id)sender {
    NSURLSessionConfiguration *defaultConfigObject = [NSURLSessionConfiguration defaultSessionConfiguration];
    NSURLSession *defaultSession = [NSURLSession sessionWithConfiguration:defaultConfigObject];
    
    NSURL *url = [NSURL URLWithString:@"http://211.249.60.54/party/guest"];
    NSMutableURLRequest *submitRequest = [NSMutableURLRequest requestWithURL:url];
    
    [submitRequest setValue:@"application/json" forHTTPHeaderField:@"content-Type"];
    [submitRequest setHTTPMethod:@"POST"];
    
    NSDictionary *submitData = @{
                                 @"name" : _nameTextField.text,
                                 @"email" : _emailLabel.text,
                                 @"age": _ageTextField.text,
                                 @"gender": _genderTextField.text,
                                 @"language": _languageTextField.text,
                                 @"date": _date,
                                 @"theme": _themeTextField.text,
                                 @"attraction": _attractionTextField.text
                                 };
    
    [submitRequest setHTTPBody:[NSJSONSerialization dataWithJSONObject:submitData options:0 error:nil]];
    
    NSURLSessionDataTask *dataTask = [defaultSession dataTaskWithRequest:submitRequest completionHandler:^(NSData *data, NSURLResponse *response, NSError *error) {
        if (error == nil) {
            NSString *request = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
            NSLog(@"Data = %@", request);
        }
        NSLog(@"%@", response);
    }];
    
    [dataTask resume];
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if ([[segue identifier] isEqualToString:@"calendarSegue"]) {
        CalendarViewController *calendarViewController = [segue destinationViewController];
        calendarViewController.transitioningDelegate = self;
        calendarViewController.modalPresentationStyle = UIModalPresentationCustom;
        _blurView.hidden = NO;
    }
}

- (id<UIViewControllerAnimatedTransitioning>)animationControllerForPresentedController:(UIViewController *)presented presentingController:(UIViewController *)presenting sourceController:(UIViewController *)source {
    ApplyViewController *animator = [[ApplyViewController alloc] init];
    animator.isShow = YES;
    return animator;
}

- (NSTimeInterval)transitionDuration:(id<UIViewControllerContextTransitioning>)transitionContext {
    return 1.0;
}

- (void)animateTransition:(id<UIViewControllerContextTransitioning>)transitionContext {
    ApplyViewController *viewControllerA = [transitionContext viewControllerForKey:UITransitionContextFromViewControllerKey];
    CalendarViewController *viewControllerB = [transitionContext viewControllerForKey:UITransitionContextToViewControllerKey];
    UIView *toView = viewControllerB.view;
    UIView *containerView = [transitionContext containerView];
    CGRect initialFrame = [transitionContext initialFrameForViewController:viewControllerA];
    CGRect offscreenRect = CGRectOffset(initialFrame, 0, -[UIScreen mainScreen].bounds.size.height);
    
    if (self.isShow) {
        CGRect viewFrame = CGRectMake(10, 100, 300, 400);
        
        toView.frame = offscreenRect;
        [containerView addSubview:toView];
        
        [UIView animateWithDuration:[self transitionDuration:transitionContext]
                              delay:0
             usingSpringWithDamping:0.5
              initialSpringVelocity:1
                            options:0
                         animations: ^{
                             toView.frame = viewFrame;
                         } completion: ^(BOOL finished) {
                             [transitionContext completeTransition:![transitionContext transitionWasCancelled]];
                         }];
    }
}

//- (IBAction)clickedMaleCheckBox:(id)sender {
//    if (_femaleCheckBox.isChecked == TRUE) {
//        _femaleCheckBox.isChecked = FALSE;
//        NSLog(@"female이 투루였다");
//    }
//    _genderString = @"male";
//}
//
//- (IBAction)clickedFemaleCheckBox:(id)sender {
//    if (_maleCheckBox.isChecked == TRUE) {
//        _maleCheckBox.isChecked = FALSE;
//        NSLog(@"male이 투루였다");
//
//    }
//    _genderString = @"female";
//}
//
//- (void) checkAction{
//    if (_maleCheckBox.isChecked == true) {
//        cbox.text = @"Checked";
//    }
//    else{
//        cbox.text = @"Unchecked";
//    }
//}

- (IBAction)clickedBackBtn:(id)sender {
    [self dismissViewControllerAnimated:YES completion:nil];

}

@end
