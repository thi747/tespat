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

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Categoria query and add missing value', () => {
      const bem: IBem = { id: 456 };
      const categoria: ICategoria = { id: 3928 };
      bem.categoria = categoria;

      const categoriaCollection: ICategoria[] = [{ id: 12236 }];
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
      const bem: IBem = { id: 456 };
      const fornecedor: IFornecedor = { id: 9899 };
      bem.fornecedor = fornecedor;

      const fornecedorCollection: IFornecedor[] = [{ id: 1545 }];
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

    it('Should update editForm', () => {
      const bem: IBem = { id: 456 };
      const categoria: ICategoria = { id: 23315 };
      bem.categoria = categoria;
      const fornecedor: IFornecedor = { id: 1815 };
      bem.fornecedor = fornecedor;

      activatedRoute.data = of({ bem });
      comp.ngOnInit();

      expect(comp.categoriasSharedCollection).toContain(categoria);
      expect(comp.fornecedorsSharedCollection).toContain(fornecedor);
      expect(comp.bem).toEqual(bem);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBem>>();
      const bem = { id: 123 };
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
      const bem = { id: 123 };
      jest.spyOn(bemFormService, 'getBem').mockReturnValue({ id: null });
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
      const bem = { id: 123 };
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
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(categoriaService, 'compareCategoria');
        comp.compareCategoria(entity, entity2);
        expect(categoriaService.compareCategoria).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareFornecedor', () => {
      it('Should forward to fornecedorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(fornecedorService, 'compareFornecedor');
        comp.compareFornecedor(entity, entity2);
        expect(fornecedorService.compareFornecedor).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
