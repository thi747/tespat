import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FornecedorService } from '../service/fornecedor.service';
import { IFornecedor } from '../fornecedor.model';
import { FornecedorFormService } from './fornecedor-form.service';

import { FornecedorUpdateComponent } from './fornecedor-update.component';

describe('Fornecedor Management Update Component', () => {
  let comp: FornecedorUpdateComponent;
  let fixture: ComponentFixture<FornecedorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fornecedorFormService: FornecedorFormService;
  let fornecedorService: FornecedorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), FornecedorUpdateComponent],
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
      .overrideTemplate(FornecedorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FornecedorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fornecedorFormService = TestBed.inject(FornecedorFormService);
    fornecedorService = TestBed.inject(FornecedorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const fornecedor: IFornecedor = { nome: 'CBA' };

      activatedRoute.data = of({ fornecedor });
      comp.ngOnInit();

      expect(comp.fornecedor).toEqual(fornecedor);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFornecedor>>();
      const fornecedor = { nome: 'ABC' };
      jest.spyOn(fornecedorFormService, 'getFornecedor').mockReturnValue(fornecedor);
      jest.spyOn(fornecedorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fornecedor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fornecedor }));
      saveSubject.complete();

      // THEN
      expect(fornecedorFormService.getFornecedor).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fornecedorService.update).toHaveBeenCalledWith(expect.objectContaining(fornecedor));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFornecedor>>();
      const fornecedor = { nome: 'ABC' };
      jest.spyOn(fornecedorFormService, 'getFornecedor').mockReturnValue({ nome: null });
      jest.spyOn(fornecedorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fornecedor: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fornecedor }));
      saveSubject.complete();

      // THEN
      expect(fornecedorFormService.getFornecedor).toHaveBeenCalled();
      expect(fornecedorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFornecedor>>();
      const fornecedor = { nome: 'ABC' };
      jest.spyOn(fornecedorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fornecedor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fornecedorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
