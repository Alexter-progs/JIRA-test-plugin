AJS.toInit(function(){
    jQuery.namespace("JIRA.Admin.Product.ProductRow");
    
    JIRA.Admin.Product.ProductRow = AJS.RestfulTable.Row.extend({
        initialize: function () {
            AJS.RestfulTable.Row.prototype.initialize.apply(this, arguments);
    
            this.events["click .project-config-product-delete"] = "_delete";
            this.delegateEvents();
        },
        _delete: function (e) {
            if (!confirm('Delete product?'))
                return;
            var row = this;
            this.model.destroy({
                data: this.model.toJSON(), success: function () {
                    row.remove();
                }
            });
            e.preventDefault();
        },
    
        render: function () {
            var data = this.model.toJSON(),
                id = this.model.get("id"),
                $el = this.$el;
            $el.attr("id", "product-" + id + "-row").attr("data-id", id);
            $el.html(JIRA.Templates.Product.productRow({
                product: data
            }));
            return this;
        }
    });
});