// Main JavaScript for Real Estate Frontend

$(document).ready(function() {
    // Initialize tooltips
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // Initialize popovers
    var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
    var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl);
    });

    // Auto-hide alerts after 5 seconds
    $('.alert').each(function() {
        var alert = $(this);
        setTimeout(function() {
            alert.fadeOut('slow');
        }, 5000);
    });

    // Smooth scrolling for anchor links
    $('a[href^="#"]').on('click', function(event) {
        var target = $(this.getAttribute('href'));
        if( target.length ) {
            event.preventDefault();
            $('html, body').stop().animate({
                scrollTop: target.offset().top - 80
            }, 1000);
        }
    });

    // Image gallery functionality
    $('.property-gallery img').on('click', function() {
        var newSrc = $(this).attr('src');
        var mainImage = $('.main-image');
        
        // Remove active class from all thumbnails
        $('.property-gallery img').removeClass('active');
        // Add active class to clicked thumbnail
        $(this).addClass('active');
        
        // Fade out, change source, fade in
        mainImage.fadeOut(200, function() {
            mainImage.attr('src', newSrc).fadeIn(200);
        });
    });

    // Price range slider (if exists)
    if ($('#priceRange').length) {
        $('#priceRange').on('input', function() {
            var value = $(this).val();
            $('#priceValue').text(formatPrice(value));
        });
    }

    // Search form auto-submit on filter change
    $('.auto-submit').on('change', function() {
        $(this).closest('form').submit();
    });

    // Property favorite toggle
    $('.btn-favorite').on('click', function(e) {
        e.preventDefault();
        var btn = $(this);
        var heart = btn.find('i');
        
        // Toggle heart icon
        if (heart.hasClass('fas')) {
            heart.removeClass('fas').addClass('far');
            btn.removeClass('btn-danger').addClass('btn-outline-danger');
        } else {
            heart.removeClass('far').addClass('fas');
            btn.removeClass('btn-outline-danger').addClass('btn-danger');
        }
        
        // Here you would typically make an AJAX call to save the favorite
        showToast('Đã cập nhật danh sách yêu thích!', 'success');
    });

    // Contact form modal
    $('#contactModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var propertyTitle = button.data('property-title');
        var modal = $(this);
        modal.find('.modal-title').text('Liên hệ về: ' + propertyTitle);
        modal.find('#propertyTitle').val(propertyTitle);
    });

    // Form validation
    $('form.needs-validation').on('submit', function(event) {
        if (this.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }
        $(this).addClass('was-validated');
    });

    // Lazy loading for images
    if ('IntersectionObserver' in window) {
        var imageObserver = new IntersectionObserver(function(entries, observer) {
            entries.forEach(function(entry) {
                if (entry.isIntersecting) {
                    var img = entry.target;
                    img.src = img.dataset.src;
                    img.classList.remove('lazy');
                    imageObserver.unobserve(img);
                }
            });
        });

        document.querySelectorAll('img[data-src]').forEach(function(img) {
            imageObserver.observe(img);
        });
    }

    // Loading states for buttons
    $('.btn-loading').on('click', function() {
        var btn = $(this);
        var originalText = btn.text();
        btn.prop('disabled', true)
           .html('<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Đang xử lý...');
        
        // Re-enable after 3 seconds (adjust based on your needs)
        setTimeout(function() {
            btn.prop('disabled', false).text(originalText);
        }, 3000);
    });

    // Copy to clipboard functionality
    $('.btn-copy').on('click', function() {
        var textToCopy = $(this).data('copy-text');
        navigator.clipboard.writeText(textToCopy).then(function() {
            showToast('Đã sao chép vào clipboard!', 'success');
        });
    });

    // Share functionality
    $('.btn-share').on('click', function(e) {
        e.preventDefault();
        var url = $(this).data('share-url') || window.location.href;
        var title = $(this).data('share-title') || document.title;
        
        if (navigator.share) {
            navigator.share({
                title: title,
                url: url
            });
        } else {
            // Fallback - copy to clipboard
            navigator.clipboard.writeText(url).then(function() {
                showToast('Đã sao chép link vào clipboard!', 'success');
            });
        }
    });

    // Auto-complete for location inputs
    if ($('.location-autocomplete').length) {
        $('.location-autocomplete').on('input', function() {
            var input = $(this);
            var query = input.val();
            
            if (query.length >= 2) {
                // Here you would typically make an AJAX call to get location suggestions
                // For now, we'll just show a placeholder
                console.log('Searching for:', query);
            }
        });
    }

    // Dynamic price formatting
    $('.price-input').on('input', function() {
        var input = $(this);
        var value = input.val().replace(/[^0-9]/g, '');
        var formatted = formatPrice(value);
        input.val(formatted);
    });

    // Property comparison
    var comparisonList = [];
    $('.btn-compare').on('click', function(e) {
        e.preventDefault();
        var propertyId = $(this).data('property-id');
        var propertyTitle = $(this).data('property-title');
        
        if (comparisonList.includes(propertyId)) {
            comparisonList = comparisonList.filter(id => id !== propertyId);
            $(this).removeClass('btn-warning').addClass('btn-outline-warning');
            showToast('Đã xóa khỏi danh sách so sánh!', 'info');
        } else {
            if (comparisonList.length >= 3) {
                showToast('Chỉ có thể so sánh tối đa 3 bất động sản!', 'warning');
                return;
            }
            comparisonList.push(propertyId);
            $(this).removeClass('btn-outline-warning').addClass('btn-warning');
            showToast('Đã thêm vào danh sách so sánh!', 'success');
        }
        
        updateComparisonButton();
    });

    // Advanced search toggle
    $('.btn-advanced-search').on('click', function() {
        $('#advancedSearchCollapse').collapse('toggle');
        var icon = $(this).find('i');
        icon.toggleClass('fa-chevron-down fa-chevron-up');
    });

    // Map integration (placeholder for Google Maps or other mapping service)
    if ($('#propertyMap').length) {
        initializeMap();
    }

    // Virtual tour integration
    $('.btn-virtual-tour').on('click', function(e) {
        e.preventDefault();
        var tourUrl = $(this).data('tour-url');
        if (tourUrl) {
            window.open(tourUrl, '_blank', 'width=1200,height=800');
        } else {
            showToast('Tour ảo chưa có sẵn cho bất động sản này.', 'info');
        }
    });

    // Print functionality
    $('.btn-print').on('click', function(e) {
        e.preventDefault();
        window.print();
    });
});

// Utility Functions

function formatPrice(price) {
    if (!price) return '';
    return new Intl.NumberFormat('vi-VN').format(price) + ' VND';
}

function showToast(message, type = 'info') {
    var toastClass = 'bg-' + (type === 'error' ? 'danger' : type);
    var toast = $(`
        <div class="toast align-items-center text-white ${toastClass} border-0 position-fixed" 
             style="top: 100px; right: 20px; z-index: 1055;" role="alert">
            <div class="d-flex">
                <div class="toast-body">${message}</div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
            </div>
        </div>
    `);
    
    $('body').append(toast);
    var toastElement = new bootstrap.Toast(toast[0]);
    toastElement.show();
    
    // Remove element after it's hidden
    toast.on('hidden.bs.toast', function() {
        $(this).remove();
    });
}

function updateComparisonButton() {
    var comparisonBtn = $('#comparisonBtn');
    if (comparisonList.length > 0) {
        comparisonBtn.show().find('.badge').text(comparisonList.length);
    } else {
        comparisonBtn.hide();
    }
}

function initializeMap() {
    // Placeholder for map initialization
    // You would integrate with Google Maps, Leaflet, or other mapping service here
    console.log('Map would be initialized here');
    
    // Example with a placeholder
    $('#propertyMap').html(`
        <div class="d-flex align-items-center justify-content-center h-100 bg-light border rounded">
            <div class="text-center text-muted">
                <i class="fas fa-map-marker-alt fa-3x mb-3"></i>
                <p>Bản đồ sẽ được hiển thị tại đây</p>
            </div>
        </div>
    `);
}

function debounce(func, wait, immediate) {
    var timeout;
    return function() {
        var context = this, args = arguments;
        var later = function() {
            timeout = null;
            if (!immediate) func.apply(context, args);
        };
        var callNow = immediate && !timeout;
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
        if (callNow) func.apply(context, args);
    };
}

// Contact form submission
function submitContactForm(formData) {
    // Here you would make an AJAX call to submit the contact form
    console.log('Contact form submitted:', formData);
    showToast('Cảm ơn bạn đã liên hệ! Chúng tôi sẽ phản hồi sớm nhất có thể.', 'success');
}

// Search suggestions
function getSearchSuggestions(query) {
    // Here you would make an AJAX call to get search suggestions
    return new Promise((resolve) => {
        setTimeout(() => {
            resolve([
                'Hà Nội', 'TP.HCM', 'Đà Nẵng', 'Hải Phòng', 'Cần Thơ'
            ].filter(city => city.toLowerCase().includes(query.toLowerCase())));
        }, 300);
    });
}

// Export functions for global use
window.RealEstate = {
    formatPrice: formatPrice,
    showToast: showToast,
    updateComparisonButton: updateComparisonButton,
    submitContactForm: submitContactForm,
    getSearchSuggestions: getSearchSuggestions
};