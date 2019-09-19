import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAchievementProgress } from 'app/shared/model/achievement-progress.model';

@Component({
  selector: 'jhi-achievement-progress-detail',
  templateUrl: './achievement-progress-detail.component.html'
})
export class AchievementProgressDetailComponent implements OnInit {
  achievementProgress: IAchievementProgress;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ achievementProgress }) => {
      this.achievementProgress = achievementProgress;
    });
  }

  previousState() {
    window.history.back();
  }
}
