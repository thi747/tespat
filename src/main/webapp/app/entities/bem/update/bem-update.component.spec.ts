import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ICategoria } from 'app/entities/categoria/categoria.model';
import { CategoriaService } from 'app/entities/categoria/service/categoria.service';
import { IFornecedor } from 'app/entities/fornecedor/fornecedor.model';
import { FornecedorService } from 'app/entities/fornecedor/service/fornecedor.service';
import { ILocal } from 'app/entities/local/local.model';
import { LocalService } from 'app/entities/local/service/local.service';
import { IBem } from '../bem.model';
import { BemService } from '../service/bem.service';
import { BemFormService } from './bem-form.service';

import { BemUpdateComponent } from './bem-update.component';

describe('Bem Management Update Component', () => {
  let comp: BemUpdateComponent;
  let fixture: ComponentFixture<BemUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bemFormService: BemFormService;
  let bemService: BemService;
  let categoriaService: CategoriaService;
  let fornecedorService: FornecedorService;
  let localService: LocalService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), BemUpdateComponent],
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
      .overrideTemplate(BemUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BemUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bemFormService = TestBed.inject(BemFormService);
    bemService = TestBed.inject(BemService);
    categoriaService = TestBed.inject(CategoriaService);
    fornecedorService = TestBed.inject(FornecedorService);
    localService = TestBed.inject(LocalService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Categoria query and add missing value', () => {
      const bem: IBem = { patrimonio: 456 };
      const categoria: ICategoria = { nome: '115b157c-f54b-4ce3-9981-cced14f0c4ad' };
      bem.categoria = categoria;

      const categoriaCollection: ICategoria[] = [{ nome: '7b2c6a36-e597-4a23-a606-098d5e921a4f' }];
      jest.spyOn(categoriaService, 'query').mockReturnValue(of(new HttpResponse({ body: categoriaCollection })));
      const additionalCategorias = [categoria];
      const expectedCollection: ICategoria[] = [...additionalCategorias, ...categoriaCollection];
      jest.spyOn(categoriaService, 'addCategoriaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bem });
      comp.ngOnInit();

      expect(categoriaService.query).toHaveBeenCalled();
      expect(categoriaService.addCategoriaToCollectionIfMissing).toHaveBeenCalledWith(
        categoriaCollection,
        ...additionalCategorias.map(expect.objectContaining),
      );
      expect(comp.categoriasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Fornecedor query and add missing value', () => {
      const bem: IBem = { patrimonio: 456 };
      const fornecedor: IFornecedor = { nome: '4400fc67-4245-4a02-96a3-58827d254020' };
      bem.fornecedor = fornecedor;

      const fornecedorCollection: IFornecedor[] = [{ nome: '95a56ba7-09d0-4480-8c54-188c98fd88f3' }];
      jest.spyOn(fornecedorService, 'query').mockReturnValue(of(new HttpResponse({ body: fornecedorCollection })));
      const additionalFornecedors = [fornecedor];
      const expectedCollection: IFornecedor[] = [...additionalFornecedors, ...fornecedorCollection];
      jest.spyOn(fornecedorService, 'addFornecedorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bem });
      comp.ngOnInit();

      expect(fornecedorService.query).toHaveBeenCalled();
      expect(fornecedorService.addFornecedorToCollectionIfMissing).toHaveBeenCalledWith(
        fornecedorCollection,
        ...additionalFornecedors.map(expect.objectContaining),
      );
      expect(comp.fornecedorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Local query and add missing value', () => {
      const bem: IBem = { patrimonio: 456 };
      const local: ILocal = { nome: 'b8c8217f-e1f3-4161-8c0c-07ae03f1692f' };
      bem.local = local;

      const localCollection: ILocal[] = [{ nome: 'b9bb548a-9f46-46b8-8655-7f09b399fe76' }];
      jest.spyOn(localService, 'query').mockReturnValue(of(new HttpResponse({ body: localCollection })));
      const additionalLocals = [local];
      const expectedCollection: ILocal[] = [...additionalLocals, ...localCollection];
      jest.spyOn(localService, 'addLocalToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bem });
      comp.ngOnInit();

      expect(localService.query).toHaveBeenCalled();
      expect(localService.addLocalToCollectionIfMissing).toHaveBeenCalledWith(
        localCollection,
        ...additionalLocals.map(expect.objectContaining),
      );
      expect(comp.localsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const bem: IBem = { patrimonio: 456 };
      const categoria: ICategoria = { nome: '4e58b354-8af5-438b-a519-63adaaf334a4' };
      bem.categoria = categoria;
      const fornecedor: IFornecedor = { nome: 'fcc8b4e8-426b-43cf-be97-b98667924a64' };
      bem.fornecedor = fornecedor;
      const local: ILocal = { nome: '903c6776-aa27-4299-9332-df9f18b29b43' };
      bem.local = local;

      activatedRoute.data = of({ bem });
      comp.ngOnInit();

      expect(comp.categoriasSharedCollection).toContain(categoria);
      expect(comp.fornecedorsSharedCollection).toContain(fornecedor);
      expect(comp.localsSharedCollection).toContain(local);
      expect(comp.bem).toEqual(bem);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBem>>();
      const bem = { patrimonio: 123 };
      jest.spyOn(bemFormService, 'getBem').mockReturnValue(bem);
      jest.spyOn(bemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bem }));
      saveSubject.complete();

      // THEN
      expect(bemFormService.getBem).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(bemService.update).toHaveBeenCalledWith(expect.objectContaining(bem));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBem>>();
      const bem = { patrimonio: 123 };
      jest.spyOn(bemFormService, 'getBem').mockReturnValue({ patrimonio: null });
      jest.spyOn(bemService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bem: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bem }));
      saveSubject.complete();

      // THEN
      expect(bemFormService.getBem).toHaveBeenCalled();
      expect(bemService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBem>>();
      const bem = { patrimonio: 123 };
      jest.spyOn(bemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bemService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCategoria', () => {
      it('Should forward to categoriaService', () => {
        const entity = { nome: 'ABC' };
        const entity2 = { nome: 'CBA' };
        jest.spyOn(categoriaService, 'compareCategoria');
        comp.compareCategoria(entity, entity2);
        expect(categoriaService.compareCategoria).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareFornecedor', () => {
      it('Should forward to fornecedorService', () => {
        const entity = { nome: 'ABC' };
        const entity2 = { nome: 'CBA' };
        jest.spyOn(fornecedorService, 'compareFornecedor');
        comp.compareFornecedor(entity, entity2);
        expect(fornecedorService.compareFornecedor).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareLocal', () => {
      it('Should forward to localService', () => {
        const entity = { nome: 'ABC' };
        const entity2 = { nome: 'CBA' };
        jest.spyOn(localService, 'compareLocal');
        comp.compareLocal(entity, entity2);
        expect(localService.compareLocal).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
