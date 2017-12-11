//
//  CalendarViewController.h
//  REAL-AMIGO
//
//  Created by YongJai on 25/10/2017.
//  Copyright © 2017 YongJai. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CalendarViewController : UIViewController

@property NSUInteger dayNum;
@property NSUInteger weekday;
@property NSUInteger month;
@property NSUInteger year;
@property NSString *selectedDate;
@property (nonatomic, retain) NSString *dateOutput;
@property (weak, nonatomic) IBOutlet UILabel *calendarErrorLabel;

@end
