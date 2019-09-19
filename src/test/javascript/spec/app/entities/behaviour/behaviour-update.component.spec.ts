import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GamifierTestModule } from '../../../test.module';
import { BehaviourUpdateComponent } from 'app/entities/behaviour/behaviour-update.component';
import { BehaviourService } from 'app/entities/behaviour/behaviour.service';
import { Behaviour } from 'app/shared/model/behaviour.model';

describe('Component Tests', () => {
  describe('Behaviour Management Update Component', () => {
    let comp: BehaviourUpdateComponent;
    let fixture: ComponentFixture<BehaviourUpdateComponent>;
    let service: BehaviourService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GamifierTestModule],
        declarations: [BehaviourUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BehaviourUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BehaviourUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BehaviourService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Behaviour(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Behaviour();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
