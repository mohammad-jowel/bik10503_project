const slides = Array.from(document.querySelectorAll('.slide'));
const counter = document.getElementById('counter');
const dots = document.getElementById('dots');
const nextBtn = document.getElementById('nextBtn');
const prevBtn = document.getElementById('prevBtn');
const downloadBtn = document.getElementById('downloadBtn');
let current = 0;

slides.forEach((_, index) => {
  const dot = document.createElement('button');
  dot.className = 'dot';
  dot.type = 'button';
  dot.setAttribute('aria-label', `Go to slide ${index + 1}`);
  dot.addEventListener('click', () => goTo(index));
  dots.appendChild(dot);
});

function refreshAnimations(slide) {
  slide.querySelectorAll('.reveal').forEach(el => {
    el.style.animation = 'none';
    void el.offsetHeight;
    el.style.animation = '';
  });
}

function goTo(index) {
  if (index < 0 || index >= slides.length) return;
  slides[current].classList.remove('active');
  current = index;
  slides[current].classList.add('active');
  refreshAnimations(slides[current]);
  updateUI();
}

function updateUI() {
  const number = String(current + 1).padStart(2, '0');
  const total = String(slides.length).padStart(2, '0');
  counter.textContent = `${number} / ${total}`;
  document.querySelectorAll('.dot').forEach((dot, i) => dot.classList.toggle('active', i === current));
  document.title = `${number} · ${slides[current].dataset.title} | BIK10503`;
}

function toggleFullscreen() {
  if (!document.fullscreenElement) document.documentElement.requestFullscreen?.();
  else document.exitFullscreen?.();
}

async function downloadSlide() {
  const slide = slides[current];
  const title = slide.dataset.title.toLowerCase().replace(/\s+/g, '-');
  const filename = `slide-${String(current + 1).padStart(2, '0')}-${title}.png`;

  if (!downloadBtn) return;
  const originalBtnContent = downloadBtn.innerHTML;
  downloadBtn.innerHTML = 'Capturing...';
  downloadBtn.style.opacity = '0.7';
  downloadBtn.disabled = true;

  try {
    const canvas = await html2canvas(slide, {
      scale: 4, // Higher resolution for better quality
      useCORS: true,
      allowTaint: true,
      backgroundColor: '#020617',
      logging: false,
      onclone: (clonedDoc) => {
        const clonedSlide = clonedDoc.querySelectorAll('.slide')[current];
        clonedSlide.style.display = 'block';
        clonedSlide.style.transform = 'none';
        
        // Force all reveal elements to be visible in the capture
        clonedSlide.querySelectorAll('.reveal').forEach(el => {
          el.style.opacity = '1';
          el.style.transform = 'none';
          el.style.animation = 'none';
          el.style.visibility = 'visible';
        });
      }
    });

    const link = document.createElement('a');
    link.download = filename;
    link.href = canvas.toDataURL('image/png', 1.0);
    link.click();
  } catch (err) {
    console.error('Download failed:', err);
    alert('Failed to capture slide as image.');
  } finally {
    downloadBtn.innerHTML = originalBtnContent;
    downloadBtn.style.opacity = '1';
    downloadBtn.disabled = false;
  }
}

nextBtn.addEventListener('click', () => goTo(current + 1));
prevBtn.addEventListener('click', () => goTo(current - 1));
downloadBtn?.addEventListener('click', downloadSlide);

document.addEventListener('keydown', event => {
  const key = event.key.toLowerCase();
  if (key === 'arrowright' || key === ' ') goTo(current + 1);
  if (key === 'arrowleft') goTo(current - 1);
  if (key === 'home') goTo(0);
  if (key === 'end') goTo(slides.length - 1);
  if (key === 'f') toggleFullscreen();
  if (key === 'd') downloadSlide();
});

updateUI();
