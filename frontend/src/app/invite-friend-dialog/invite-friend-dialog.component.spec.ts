import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InviteFriendDialogComponent } from './invite-friend-dialog.component';

describe('InviteFriendDialogComponent', () => {
  let component: InviteFriendDialogComponent;
  let fixture: ComponentFixture<InviteFriendDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InviteFriendDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InviteFriendDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
