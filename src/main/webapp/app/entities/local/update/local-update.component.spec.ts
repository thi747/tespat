import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LocalService } from '../service/local.service';
import { ILocal } from '../local.model';
import { LocalFormService } from './local-form.service';

import { LocalUpdateComponent } from './local-update.component';

describe('Local Management Update Component', () => {
  let comp: LocalUpdateComponent;
  let fixture: ComponentFixture<LocalUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let localFormService: LocalFormService;
  let localService: LocalService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), LocalUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(LocalUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LocalUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    localFormService = TestBed.inject(LocalFormService);
    localService = TestBed.inject(LocalService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const local: ILocal = { nome: 'CBA' };

      activatedRoute.data = of({ local });
      comp.ngOnInit();

      expect(comp.local).toEqual(local);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILocal>>();
      const local = { nome: 'ABC' };
      jest.spyOn(localFormService, 'getLocal').mockReturnValue(local);
      jest.spyOn(localService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ local });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: local }));
      saveSubject.complete();

      // THEN
      expect(localFormService.getLocal).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(localService.update).toHaveBeenCalledWith(expect.objectContaining(local));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILocal>>();
      const local = { nome: 'ABC' };
      jest.spyOn(localFormService, 'getLocal').mockReturnValue({ nome: null });
      jest.spyOn(localService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ local: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: local }));
      saveSubject.complete();

      // THEN
      expect(localFormService.getLocal).toHaveBeenCalled();
      expect(localService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILocal>>();
      const local = { nome: 'ABC' };
      jest.spyOn(localService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ local });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(localService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
