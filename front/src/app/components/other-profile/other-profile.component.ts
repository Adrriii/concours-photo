import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../../services/user.service';
import {User} from '../../models/User.model';

@Component({
  selector: 'app-other-profile',
  templateUrl: './other-profile.component.html',
  styleUrls: ['./other-profile.component.css']
})

export class OtherProfileComponent implements OnInit {
    private id: number;
    public UserQueried: User = null;

  constructor(
      private route: ActivatedRoute,
      private userService: UserService
  ) { }

  ngOnInit(): void {
      const stringId = this.route.snapshot.paramMap.get('id');
      this.id = Number(stringId);
      this.userService.getById(this.id).subscribe((userQueried) => this.UserQueried = userQueried);
  }

}
