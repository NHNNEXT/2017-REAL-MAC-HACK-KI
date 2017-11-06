//
//  CalendarViewController.m
//  REAL-AMIGO
//
//  Created by YongJai on 25/10/2017.
//  Copyright © 2017 YongJai. All rights reserved.
//

#import "CalendarViewController.h"

@interface CalendarViewController () {
    NSInteger buttonTag;
    UIButton *previousBtn;
}
@end

@implementation CalendarViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self presentFirstCalendar];
    [self nextButton];
    [self prevButton];
    
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)createCalendar {
    NSCalendar *gregorian = [[NSCalendar alloc]initWithCalendarIdentifier:NSCalendarIdentifierGregorian];
    NSCalendar *calendar = [NSCalendar currentCalendar];
    NSDateComponents *components = [[NSDateComponents alloc] init];
    [components setDay:1];
    [components setMonth:_month];
    [components setYear:_year];
    NSDate * newDate = [calendar dateFromComponents:components];
    NSDateComponents *newComponents = [gregorian components:NSCalendarUnitWeekday fromDate:newDate];
    _weekday=[newComponents weekday];
    
    _dayNum = [self getCurrentDateInfo:newDate];
    NSUInteger newWeekDay = _weekday - 1;
    
    int yVal = 53;
    int count = 1;
    
    NSDateFormatter *day = [[NSDateFormatter alloc]init];
    [day setDateFormat:@"YYYYMd"];
    
    UILabel *yearMonth = [[UILabel alloc]initWithFrame:CGRectMake(75, 10, 150, 30)];
//    [yearMonth setFont:[UIFont systemFontOfSize:18]];
    [yearMonth setText:[NSString stringWithFormat:@"%ld . %ld",(long)_year ,(long)_month]];
    [yearMonth setFont:[UIFont boldSystemFontOfSize:20]];
    [yearMonth setTextColor:[UIColor blackColor]];
    yearMonth.textAlignment = NSTextAlignmentCenter;
    yearMonth.tag = 32;
    [self.view addSubview:yearMonth];
    
    for (int i = 0; i < 7; i ++) {
        NSArray *dayNameArr = [NSArray arrayWithObjects:@"Sun", @"Mon", @"Tue", @"Wed", @"Thu", @"Fri", @"Sat", nil];
        UILabel *dayNameLabel = [[UILabel alloc]initWithFrame:CGRectMake(13 + (50 * i), 50 , 30, 30)];
        dayNameLabel.textAlignment = NSTextAlignmentCenter;
        [dayNameLabel setFont:[UIFont systemFontOfSize:12]];
        [dayNameLabel setText:dayNameArr[i]];
        [dayNameLabel setTextColor:[UIColor grayColor]];
        [self.view addSubview:dayNameLabel];
    }
    
    for (int startDay = 1; startDay <= _dayNum; startDay++) {
        UIButton *dayNumBtn = [UIButton buttonWithType: UIButtonTypeRoundedRect];
//        NSString *date = [NSString stringWithFormat:@"%ld%ld%d", (long)_year, (long)_month, startDay];
        NSUInteger xCoord = (newWeekDay * 50) + 13;
        NSUInteger yCoord = (count * 45) + yVal;
        newWeekDay++;
        
        if (newWeekDay > 6) {
            newWeekDay = 0;
            count++;
        }
        
        dayNumBtn.frame = CGRectMake(xCoord, yCoord, 30, 30);
        [dayNumBtn setTitle:[NSString stringWithFormat:@"%d",startDay]forState:UIControlStateNormal];
        [dayNumBtn setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
        
        [dayNumBtn addTarget:self action:@selector(dayNumButtonClicked:) forControlEvents:UIControlEventTouchUpInside];
      
        dayNumBtn.layer.cornerRadius = 40/2.0f;
        dayNumBtn.backgroundColor = [UIColor colorWithRed:171 green:178 blue:186 alpha:1.0f];

        
        dayNumBtn.tag = startDay;
        
        if (newWeekDay > 0 && newWeekDay < 2) {
            [dayNumBtn setTitleColor:[UIColor redColor] forState:UIControlStateNormal];
        } else if (newWeekDay == 0) {
            [dayNumBtn setTitleColor:[UIColor grayColor] forState:UIControlStateNormal];
        }
        [self.view addSubview:dayNumBtn];
    }
}

- (void)presentFirstCalendar {
    NSCalendar *calendar = [NSCalendar currentCalendar];
    NSDateComponents *components = [calendar components:NSCalendarUnitMonth|NSCalendarUnitYear fromDate:[NSDate date]];
    _year = [components year];
    _month = [components month];
    
    [self createCalendar];
}

- (void)prevButton {
    UIButton *prevBtn = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    [prevBtn setFrame:CGRectMake(34, 10, 30, 30)];
    [prevBtn setTitle:@"<" forState:UIControlStateNormal];
    [prevBtn.titleLabel setFont:[UIFont systemFontOfSize:20]];
    [prevBtn setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    [prevBtn addTarget:self action:@selector(prev:) forControlEvents:UIControlEventTouchUpInside];
    prevBtn.contentHorizontalAlignment = UIControlContentHorizontalAlignmentLeft;
    [self.view addSubview:prevBtn];
}

- (void)nextButton {
    UIButton *nextBtn = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    [nextBtn setFrame:CGRectMake(236, 10, 30, 30)];
    [nextBtn setTitle:@">" forState:UIControlStateNormal];
    [nextBtn.titleLabel setFont:[UIFont systemFontOfSize:20]];
    [nextBtn setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    [nextBtn addTarget:self action:@selector(next:) forControlEvents:UIControlEventTouchUpInside];
    nextBtn.contentHorizontalAlignment = UIControlContentHorizontalAlignmentRight;
    [self.view addSubview:nextBtn];
}

- (void)next:(id)sender {
    _month++;
    [self removeTag];
    [self updateCalNow];
}

- (void)prev:(id)sender {
    _month--;
    [self removeTag];
    [self updateCalNow];
}

- (void)updateCalNow {
    if (_month > 12) {
        _month = 1;
        _year++;
    } else if (_month < 1) {
        _month = 12;
        _year--;
    }
    [self createCalendar];
}

- (NSUInteger)getCurrentDateInfo:(NSDate *)myDate {
    NSCalendar *calendar = [NSCalendar currentCalendar];
    NSRange range = [calendar rangeOfUnit:NSCalendarUnitDay inUnit:NSCalendarUnitMonth forDate:myDate];
    NSUInteger numberOfDaysInMonth = range.length;
    return numberOfDaysInMonth;
}

- (void)removeTag {
    int x = 1;
    while (x <= 40) {
        [[self.view viewWithTag:x]removeFromSuperview];
        x++;
    }
}

- (void)dayNumButtonClicked:(id)sender {
    UIButton *btn = (UIButton *)sender;
    
    // 이전에 눌린 버튼의 색을 없애줘야 함
    if (buttonTag != 100) {
        previousBtn.tag = buttonTag;
        previousBtn.backgroundColor = [UIColor whiteColor];
    }
    buttonTag = btn.tag;
    _selectedDate = [NSString stringWithFormat:@"%ld.%ld.%ld", (long)_year, (long)_month, (long)btn.tag];
    btn.backgroundColor = [UIColor yellowColor];
    previousBtn = (UIButton *)sender;
}

- (void)dateFormatter {
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"YYYY-MM-dd"];
    NSDate *date = [[NSDate alloc] init];
    date = [dateFormatter dateFromString:_selectedDate];
    NSDate *dateFromString = [dateFormatter dateFromString:_selectedDate];
    [dateFormatter setDateFormat:@"YYYY.MM.dd"];
    _dateOutput = [dateFormatter stringFromDate:dateFromString];
}

- (IBAction)clickedDismissBtn:(id)sender {
    [self dismissViewControllerAnimated:YES completion:nil];
    [[NSNotificationCenter defaultCenter] postNotificationName:@"dismissNoti" object:nil];
}

- (void) hideLabel{
    _calendarErrorLabel.hidden = YES;
}

- (IBAction)clickedConfirmBtn:(id)sender {
    if (_selectedDate.length == 0) {
        _calendarErrorLabel.hidden = NO;
        [self performSelector:@selector(hideLabel) withObject:nil afterDelay:1];
    } else {
//        [self dateFormatter];
        NSLog(@"%@", _selectedDate);

        [[NSNotificationCenter defaultCenter] postNotificationName:@"dateNoti" object:_selectedDate];
        [self dismissViewControllerAnimated:YES completion:nil];
    }

}



/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
