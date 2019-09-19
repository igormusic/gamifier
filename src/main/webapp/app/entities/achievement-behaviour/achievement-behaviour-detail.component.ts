import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAchievementBehaviour } from 'app/shared/model/achievement-behaviour.model';

@Component({
  selector: 'jhi-achievement-behaviour-detail',
  templateUrl: './achievement-behaviour-detail.component.html'
})
export class AchievementBehaviourDetailComponent implements OnInit {
  achievementBehaviour: IAchievementBehaviour;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ achievementBehaviour }) => {
      this.achievementBehaviour = achievementBehaviour;
    });
  }

  previousState() {
    window.history.back();
  }
}
