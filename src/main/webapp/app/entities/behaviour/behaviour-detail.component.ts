import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBehaviour } from 'app/shared/model/behaviour.model';

@Component({
  selector: 'jhi-behaviour-detail',
  templateUrl: './behaviour-detail.component.html'
})
export class BehaviourDetailComponent implements OnInit {
  behaviour: IBehaviour;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ behaviour }) => {
      this.behaviour = behaviour;
    });
  }

  previousState() {
    window.history.back();
  }
}
