jQuery(function(){
    var $table = AJS.$("#project-config-products-table");
    
    function getResourceURL(){
        return contextPath + "/rest/productresource/1.0/products";
    }
    
    function getProduct(callback) {
        JIRA.SmartAjax.makeRequest({
            url: getResourceURL(),
            complete: function (xhr, status, response) {
                if (response.successful){
                    callback(response.data.products);
                } else {
                    $table.trigger("serverError",
                                [JIRA.SmartAjax.buildSimpleErrorContent(response)]);
                }                
            }
        });
    }
    
    getProduct(function(products) {
        window.ttt = products;
        JIRA.Admin.ProductTable = new AJS.RestfulTable({
            el: $table,
            loadingMsg: "Loading table...",
            allowCreate: true,
            url: getResourceURL(),
            editable: true,
            resources: {
                all: getResourceURL(),
                self: getResourceURL()
            },
            columns: [
                {
                    id: "productName",
                    header: "Product name"
                },
                {
                    id: "manufacturer",
                    header: "Manufacturer"
                },
                {
                    id: "manufactureDate",
                    header: "Manufacture date"
                },
                {
                    id: "expirationDate",
                    header: "Expiration date"
                },
                {
                    id: "description",
                    header: "Description"
                }
            ],
            views: {
                editRow: JIRA.Admin.Product.EditProductRow,
                row: JIRA.Admin.Product.ProductRow
            }
        });
        for (var i = 0; i < products.length; i++){
            JIRA.Admin.ProductTable.addRow(products[i]);
        }
        JIRA.userhover($table);
    });
});