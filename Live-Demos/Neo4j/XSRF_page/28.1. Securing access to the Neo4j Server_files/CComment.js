jQuery(document).ready(function(){
	jQuery(function() {
            jQuery('#CComment_name').keyup(function() {
                if (this.value.match(/[^a-zA-Z0-9 _]/g)) {
                    this.value = this.value.replace(/[^a-zA-Z0-9 _]/g, '');
                }
            });
        });
	var working = false;
	jQuery(".cc_delete").bind("click",function(e){
 		e.preventDefault();
		if(working) return false;
		var answer = confirm("Are you sure you want to delete this field?(all the comments data of this field will be deleted!)");
		if (!answer) return false;
		working = true;
		var this_button = this;
		var name = jQuery(this).parent().parent().parent().children(":first").text().trim();
		jQuery(this).val("Deleting");
		jQuery.post(CComment_ajax_var.ajaxurl,{action:"CComment-submit", nonce:CComment_ajax_var.nonce, CC_delete:1,CC_name:name},function(response){
			if(response == "1"){
				jQuery(this_button).parent().hide("slow",function(){
					jQuery(this_button).closest("tr").remove();
				});
			}
			else{
				alert(response);
				jQuery(this_button).val("Delete");
			}
		});
		working = false;
		return false;
	});
	jQuery(".cc_edit").bind("click",function(e){
 		e.preventDefault();
		if(jQuery(this).val() == "Edit"){
			if(working) return false;
			working = true;
			var desc = jQuery(this).parent().prev().text().trim();
			desc = '<input type="text" value="'+desc+'" />';
			jQuery(this).val('Update');
			jQuery(this).parent().prev().html(desc);
		}
		else{
			var name = jQuery(this).parent().prev().prev().text().trim();
			var new_desc = jQuery(this).parent().prev().children(":first").val();
			var this_button = this;
			jQuery(this).val('Updating');
			jQuery.post(CComment_ajax_var.ajaxurl,{action:"CComment-submit", nonce:CComment_ajax_var.nonce, CC_edit:1, CC_name:name, new_desc:new_desc},function(response){
				if(response == "1"){
					jQuery(this_button).val('Edit');
					jQuery(this_button).parent().prev().html(new_desc);
					working = false;
				}
				else
				{
					alert("An error accurred! Please try again");
				}
			});
		}
		return false;
	});
	jQuery(".cc_act").bind("click",function(e){
 		e.preventDefault();
		if(working) return false;
		working = true;
		var this_button = this;
		var name = jQuery(this).parent().parent().children(":first").text().trim();
		if(jQuery(this).val() == "Activate") {
			jQuery(this).val('Activating');
			var act = 1;
			var color = 'background-color:#FFFFFF;'
			var old_val = 'Activate';
			var new_val = 'Deactivate';
			var new_title = 'Click to deactivate this field';
		}
		else {
			jQuery(this).val('Deactivating');
			var act = 0;
			var color = 'background-color:#EEEEEE;'
			var old_val = 'Deactivate';
			var new_val = 'Activate';
			var new_title = 'Click to activate this field';
		}
		jQuery.post(CComment_ajax_var.ajaxurl,{action:"CComment-submit", nonce:CComment_ajax_var.nonce, CC_act:act,CC_name:name},function(response){
			if(response == "1"){
				jQuery(this_button).parent().parent().removeAttr('style');
				jQuery(this_button).parent().parent().attr('style', color);
				jQuery(this_button).val(new_val);
				jQuery(this_button).attr('title', new_title);
			}	
			else{
				alert(response);
				jQuery(this_button).val(old_val);
			}
			working = false;
		});
		return false;
	});
	jQuery(".cc_req").bind("click",function(e){
 		e.preventDefault();
		if(working) return false;
		working = true;
		var this_button = this;
		var name = jQuery(this).parent().parent().children(":first").text().trim();
		if(jQuery(this).val() == "Yes") {
			jQuery(this).val('Updating...');
			var req = 1;
			var old_val = 'Yes';
			var new_val = 'No';
			var new_title = 'Click to make this field not required';
		}
		else {
			jQuery(this).val('Updating...');
			var req = 0;
			var old_val = 'No';
			var new_val = 'Yes';
			var new_title = 'Click to make this field required';
		}
		jQuery.post(CComment_ajax_var.ajaxurl,{action:"CComment-submit", nonce:CComment_ajax_var.nonce, CC_req:req,CC_name:name},function(response){
			if(response == "1"){
				jQuery(this_button).val(new_val);
				jQuery(this_button).attr('title', new_title);
			}	
			else{
				alert(response);
				jQuery(this_button).val(old_val);
			}
			working = false;
		});
		return false;
	});
	jQuery("#CComment_form").bind("submit", function(e){
		e.preventDefault();
		if(working) return false;
		if(jQuery("#CComment_name").val() == ""){
			alert("The name can not be empty");
			jQuery("#CComment_name").focus();
			return false;
		}
		if(jQuery("#CComment_desc").val() == ""){
			alert("The description can not be empty");
			jQuery("#CComment_desc").focus();
			return false
		}
		working = true;
		jQuery('#add').val('Adding');
		var name = jQuery("#CComment_name").val();
		var desc = jQuery("#CComment_desc").val();
		jQuery.post(CComment_ajax_var.ajaxurl,{action:"CComment-submit", nonce:CComment_ajax_var.nonce, CC_add:1, CC_name:name,CC_desc:desc},function(response){
			if(response == "1"){
				jQuery("#CComment_table").append('<tr><td>'+name+'</td><td>'+desc+'</td><td><input type="button" class="edit" value="Edit" title="Click to edit this field" /></td><td><input type="button" class="act" value="Deactivate" title="Click to deactivate this field" /></td><td><input type="button" class="req" value="Yes" title="Click to make this field required" /></td><td><input type="button" class="delete" value="Delete" title="Click to delete this field" /></td></tr>');
				jQuery('#add').val('Add');
				jQuery("#CComment_name").val("");
				jQuery("#CComment_desc").val("");
				jQuery("#CComment_name").focus();
			}
			else {
				alert(response);
				jQuery("#add").val("Add");
				jQuery("#CComment_name").focus();
			}
		});
		working = false;
	});
});