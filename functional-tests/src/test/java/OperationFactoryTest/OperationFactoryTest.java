package OperationFactoryTest;

import com.adrian.interview.model.QueryRequestModel;
import com.adrian.interview.model.value.QueryOperation;
import com.adrian.interview.operation.OperationFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class OperationFactoryTest {

    private OperationFactory operationFactory;
    private QueryRequestModel requestModel;

    @BeforeEach
    void setup() {
        operationFactory = new OperationFactory(null);
        requestModel = new QueryRequestModel();
        requestModel.setAction(QueryOperation.COUNT);
    }

    @Test
    @DisplayName("Test get operation")
    void testGetOperation() {
        assertEquals(QueryOperation.COUNT, operationFactory.getOperation(requestModel.getAction()).get().getQueryOperationType());
    }
}
