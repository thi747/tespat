import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IMovimentacao } from '../movimentacao.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../movimentacao.test-samples';

import { MovimentacaoService, RestMovimentacao } from './movimentacao.service';

const requireRestSample: RestMovimentacao = {
  ...sampleWithRequiredData,
  data: sampleWithRequiredData.data?.format(DATE_FORMAT),
};

describe('Movimentacao Service', () => {
  let service: MovimentacaoService;
  let httpMock: HttpTestingController;
  let expectedResult: IMovimentacao | IMovimentacao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MovimentacaoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Movimentacao', () => {
      const movimentacao = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(movimentacao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Movimentacao', () => {
      const movimentacao = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(movimentacao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Movimentacao', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Movimentacao', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Movimentacao', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMovimentacaoToCollectionIfMissing', () => {
      it('should add a Movimentacao to an empty array', () => {
        const movimentacao: IMovimentacao = sampleWithRequiredData;
        expectedResult = service.addMovimentacaoToCollectionIfMissing([], movimentacao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(movimentacao);
      });

      it('should not add a Movimentacao to an array that contains it', () => {
        const movimentacao: IMovimentacao = sampleWithRequiredData;
        const movimentacaoCollection: IMovimentacao[] = [
          {
            ...movimentacao,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMovimentacaoToCollectionIfMissing(movimentacaoCollection, movimentacao);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Movimentacao to an array that doesn't contain it", () => {
        const movimentacao: IMovimentacao = sampleWithRequiredData;
        const movimentacaoCollection: IMovimentacao[] = [sampleWithPartialData];
        expectedResult = service.addMovimentacaoToCollectionIfMissing(movimentacaoCollection, movimentacao);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(movimentacao);
      });

      it('should add only unique Movimentacao to an array', () => {
        const movimentacaoArray: IMovimentacao[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const movimentacaoCollection: IMovimentacao[] = [sampleWithRequiredData];
        expectedResult = service.addMovimentacaoToCollectionIfMissing(movimentacaoCollection, ...movimentacaoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const movimentacao: IMovimentacao = sampleWithRequiredData;
        const movimentacao2: IMovimentacao = sampleWithPartialData;
        expectedResult = service.addMovimentacaoToCollectionIfMissing([], movimentacao, movimentacao2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(movimentacao);
        expect(expectedResult).toContain(movimentacao2);
      });

      it('should accept null and undefined values', () => {
        const movimentacao: IMovimentacao = sampleWithRequiredData;
        expectedResult = service.addMovimentacaoToCollectionIfMissing([], null, movimentacao, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(movimentacao);
      });

      it('should return initial array if no Movimentacao is added', () => {
        const movimentacaoCollection: IMovimentacao[] = [sampleWithRequiredData];
        expectedResult = service.addMovimentacaoToCollectionIfMissing(movimentacaoCollection, undefined, null);
        expect(expectedResult).toEqual(movimentacaoCollection);
      });
    });

    describe('compareMovimentacao', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMovimentacao(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMovimentacao(entity1, entity2);
        const compareResult2 = service.compareMovimentacao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMovimentacao(entity1, entity2);
        const compareResult2 = service.compareMovimentacao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMovimentacao(entity1, entity2);
        const compareResult2 = service.compareMovimentacao(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
